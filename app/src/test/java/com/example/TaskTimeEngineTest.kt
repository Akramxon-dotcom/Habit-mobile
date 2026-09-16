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

    @Test
    fun testAllSevenDaysOfWeekTypes() {
        // Calendar.SUNDAY = 1, MONDAY = 2, TUESDAY = 3, WEDNESDAY = 4, THURSDAY = 5, FRIDAY = 6, SATURDAY = 7
        val sundayType = TaskTimeEngine.getDayTypeByCalendarDay(java.util.Calendar.SUNDAY)
        val mondayType = TaskTimeEngine.getDayTypeByCalendarDay(java.util.Calendar.MONDAY)
        val tuesdayType = TaskTimeEngine.getDayTypeByCalendarDay(java.util.Calendar.TUESDAY)
        val wednesdayType = TaskTimeEngine.getDayTypeByCalendarDay(java.util.Calendar.WEDNESDAY)
        val thursdayType = TaskTimeEngine.getDayTypeByCalendarDay(java.util.Calendar.THURSDAY)
        val fridayType = TaskTimeEngine.getDayTypeByCalendarDay(java.util.Calendar.FRIDAY)
        val saturdayType = TaskTimeEngine.getDayTypeByCalendarDay(java.util.Calendar.SATURDAY)

        println("Yakshanba = ${sundayType.label}")
        println("Dushanba = ${mondayType.label}")
        println("Seshanba = ${tuesdayType.label}")
        println("Chorshanba = ${wednesdayType.label}")
        println("Payshanba = ${thursdayType.label}")
        println("Juma = ${fridayType.label}")
        println("Shanba = ${saturdayType.label}")

        assertEquals("YAKSHANBA — YENGIL KUN", sundayType.label)
        assertEquals("DARS KUNI", mondayType.label)
        assertEquals("DARS YO'Q KUN", tuesdayType.label)
        assertEquals("DARS KUNI", wednesdayType.label)
        assertEquals("DARS YO'Q KUN", thursdayType.label)
        assertEquals("DARS KUNI", fridayType.label)
        assertEquals("DARS YO'Q KUN", saturdayType.label)
    }

    @Test
    fun testSundayPlanHasZeroSchoolAndZeroRtm() {
        val plan = TaskTimeEngine.SUNDAY_PLAN
        assertEquals(27, plan.size)

        val schoolOrRtmTasks = plan.filter {
            it.category.equals("school", ignoreCase = true) ||
            it.category.equals("rtm", ignoreCase = true) ||
            it.title.contains("Maktab", ignoreCase = true) ||
            it.title.contains("RTM", ignoreCase = true)
        }
        assertEquals("Yakshanba kuni maktab yoki RTM umuman bo'lmasligi shart!", 0, schoolOrRtmTasks.size)

        // Verify key tasks in Sunday
        assertEquals("04:05", plan.first().start)
        assertEquals("Uyg'onish → Tahorat → Bomdod namozi → dua", plan.first().title)
        assertEquals("22:00", plan.last().start)
        assertEquals("Uxlash", plan.last().title)

        val peshin = plan.find { it.title.contains("Peshin", ignoreCase = true) }
        assertNotNull(peshin)
        assertEquals("12:20", peshin?.start)
        assertEquals("12:52", peshin?.end)
    }
}
