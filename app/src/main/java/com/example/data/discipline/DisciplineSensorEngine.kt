package com.example.data.discipline

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Murosasiz datchiklar boshqaruvchisi:
 * - Proximity Sensor: Push-up (atjimaniya) ko'krak yaqinlashishini sanash (anti-cheat vaqt filtri bilan)
 * - Accelerometer: Squat (o'tirib-turish) to'liq amplituda sanash
 * - Accelerometer Micro-tremor: Plank holatida inson muskullari titrashini tekshirish (stolga qo'yib ketishni aniqlash)
 * - Accelerometer Rhythm: Joyida yugurish qadamlarini sanash
 */
class DisciplineSensorEngine(
    private val context: Context,
    private val onRepetitionCounted: (currentReps: Int) -> Unit,
    private val onPlankTremorTick: (isValidTremor: Boolean) -> Unit = {}
) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
    private val proximitySensor = sensorManager?.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    private val accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    var currentReps = 0
        private set

    var activeMode: DisciplineExerciseMode = DisciplineExerciseMode.NONE
        private set

    // Push-up proximity anti-cheat
    private var isChestClose = false
    private var lastProximityChangeMs = 0L
    private val MIN_PUSHUP_CYCLE_MS = 900L // Inson 0.9 soniyadan tez to'liq atjimaniya qila olmaydi

    // Squat accelerometer peak detection
    private var isSquatDown = false
    private var lastSquatMs = 0L
    private val MIN_SQUAT_CYCLE_MS = 1100L

    // Plank micro-tremor detection
    private var lastAccX = 0f
    private var lastAccY = 0f
    private var lastAccZ = 0f
    private var consecutiveTremorSamples = 0
    private var consecutiveDeadSamples = 0

    // Running on spot
    private var lastStepMs = 0L

    fun startListening(mode: DisciplineExerciseMode) {
        stopListening()
        activeMode = mode
        currentReps = 0
        isChestClose = false
        isSquatDown = false
        lastProximityChangeMs = 0L
        lastSquatMs = 0L
        lastStepMs = 0L

        when (mode) {
            DisciplineExerciseMode.PUSH_UP -> {
                // Proximity is primary, accelerometer as fallback if proximity not available
                if (proximitySensor != null) {
                    sensorManager?.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_UI)
                } else if (accelerometer != null) {
                    sensorManager?.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
                }
            }
            DisciplineExerciseMode.SQUAT,
            DisciplineExerciseMode.RUNNING_ON_SPOT,
            DisciplineExerciseMode.PLANK -> {
                if (accelerometer != null) {
                    sensorManager?.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
                }
            }
            DisciplineExerciseMode.NONE -> {}
        }
    }

    fun stopListening() {
        try {
            sensorManager?.unregisterListener(this)
        } catch (_: Exception) {}
        activeMode = DisciplineExerciseMode.NONE
    }

    fun resetReps(to: Int = 0) {
        currentReps = to
        onRepetitionCounted(currentReps)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return
        val now = System.currentTimeMillis()

        when (event.sensor.type) {
            Sensor.TYPE_PROXIMITY -> {
                if (activeMode == DisciplineExerciseMode.PUSH_UP) {
                    val distance = event.values[0]
                    val maxRange = event.sensor.maximumRange
                    val isNear = distance < (maxRange.coerceAtMost(5f))

                    if (isNear && !isChestClose) {
                        isChestClose = true
                    } else if (!isNear && isChestClose) {
                        // Chest lifted back up!
                        val cycleDuration = now - lastProximityChangeMs
                        if (cycleDuration >= MIN_PUSHUP_CYCLE_MS) {
                            currentReps++
                            lastProximityChangeMs = now
                            onRepetitionCounted(currentReps)
                        }
                        isChestClose = false
                    }
                }
            }

            Sensor.TYPE_ACCELEROMETER -> {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                when (activeMode) {
                    DisciplineExerciseMode.PUSH_UP -> {
                        // If proximity was missing, use Z-axis displacement
                        if (proximitySensor == null) {
                            val totalAcc = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
                            if (totalAcc > 13.5f && !isChestClose && now - lastProximityChangeMs > 600) {
                                isChestClose = true
                            } else if (totalAcc < 9.5f && isChestClose && now - lastProximityChangeMs > MIN_PUSHUP_CYCLE_MS) {
                                isChestClose = false
                                currentReps++
                                lastProximityChangeMs = now
                                onRepetitionCounted(currentReps)
                            }
                        }
                    }

                    DisciplineExerciseMode.SQUAT -> {
                        // Vertical acceleration drops when going down, peaks when standing up
                        val totalAcc = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
                        if (totalAcc < 7.5f && !isSquatDown && now - lastSquatMs > 500) {
                            isSquatDown = true
                        } else if (totalAcc > 12.8f && isSquatDown && now - lastSquatMs > MIN_SQUAT_CYCLE_MS) {
                            isSquatDown = false
                            currentReps++
                            lastSquatMs = now
                            onRepetitionCounted(currentReps)
                        }
                    }

                    DisciplineExerciseMode.RUNNING_ON_SPOT -> {
                        val totalAcc = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
                        // Rhythmic high impact peaks
                        if (totalAcc > 13.0f && now - lastStepMs > 320) {
                            currentReps++
                            lastStepMs = now
                            onRepetitionCounted(currentReps)
                        }
                    }

                    DisciplineExerciseMode.PLANK -> {
                        // Micro-tremor calculation: human muscles tremble slightly during plank
                        val deltaX = abs(x - lastAccX)
                        val deltaY = abs(y - lastAccY)
                        val deltaZ = abs(z - lastAccZ)
                        val deltaSum = deltaX + deltaY + deltaZ

                        // If deltaSum is virtually 0 for many samples, phone is on a table (cheating!)
                        // If deltaSum is within 0.04f .. 1.2f, it's authentic human plank holding tremor
                        val isValidTremor = deltaSum in 0.03f..1.6f
                        val isCompletelyDead = deltaSum < 0.015f

                        if (isCompletelyDead) {
                            consecutiveDeadSamples++
                            consecutiveTremorSamples = 0
                        } else if (isValidTremor) {
                            consecutiveTremorSamples++
                            consecutiveDeadSamples = 0
                        }

                        lastAccX = x
                        lastAccY = y
                        lastAccZ = z

                        // Call back every few ticks
                        val verifiedActiveHolding = consecutiveDeadSamples < 15
                        onPlankTremorTick(verifiedActiveHolding)
                    }

                    DisciplineExerciseMode.NONE -> {}
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}

enum class DisciplineExerciseMode {
    NONE,
    PUSH_UP,
    SQUAT,
    PLANK,
    RUNNING_ON_SPOT
}
