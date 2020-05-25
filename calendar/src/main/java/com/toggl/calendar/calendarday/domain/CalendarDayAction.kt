package com.toggl.calendar.calendarday.domain

import com.toggl.calendar.common.domain.CalendarAction

sealed class CalendarDayAction {
    object ExampleAction : CalendarDayAction()

    companion object {
        fun fromCalendarAction(calendarAction: CalendarAction): CalendarDayAction? =
            if (calendarAction !is CalendarAction.CalendarDay) null
            else calendarAction.calendarDay

        fun toCalendarAction(calendarDayAction: CalendarDayAction): CalendarAction =
            CalendarAction.CalendarDay(calendarDayAction)
    }
}

fun CalendarDayAction.formatForDebug() =
    when (this) {
        is CalendarDayAction.ExampleAction -> "Example Action"
    }