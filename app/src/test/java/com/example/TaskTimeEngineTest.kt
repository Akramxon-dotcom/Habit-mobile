package com.example

import com.example.data.model.HabitState
import com.example.data.model.ScheduleItem
import com.example.data.model.TaskStatus
import com.example.data.model.TaskTimeEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class TaskTimeEngineTest {

    @Test
    fun testParseMinuteOfDay() {
        assertEquals(0, TaskTimeEngine.parseMinuteOfDay("00:00"))
        assertEquals(7 * 60, TaskTimeEngine.parseMinuteOfDay("07:00"))
        assertEquals(7 * 60 + 40, TaskTimeEngine.parseMinuteOfDay("07:40"))
        assertEquals(23 * 60 + 59, TaskTimeEngine.parseMinuteOfDay("23:59"))
        assertNull(TaskTimeEngine.parseMinuteOfDay("invalid"))
        assertNull(TaskTimeEngine.parseMinuteOfDay("25:00"))
    }

    @Test
    fun testFindActiveScheduleItem() {
        val items = listOf(
            ScheduleItem(
                id = "1",
                title = "All Day Task",
                start = "00:00",
                end = "23:59"
            )
        )
        val active = TaskTimeEngine.findActiveScheduleItem(items)
        assertNotNull(active)
        assertEquals("All Day Task", active?.title)
    }

    @Test
    fun testRealtimeTaskInfoCalculation() {
        val habit = HabitState(
            title = "All Day Focus",
            start = "00:00",
            end = "23:59",
            blocking = true
        )
        val info = TaskTimeEngine.calculateTaskInfo(habit)
        assertEquals(TaskStatus.ACTIVE, info.status)
        assertTrue(info.progress in 0f..1f)
        assertTrue(info.isBlockingActiveNow)
    }
}
