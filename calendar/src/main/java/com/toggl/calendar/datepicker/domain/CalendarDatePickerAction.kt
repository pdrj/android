package com.toggl.calendar.datepicker.domain

import com.toggl.calendar.common.domain.CalendarAction

sealed class CalendarDatePickerAction {
    object ExampleAction : CalendarDatePickerAction()

    companion object {
        fun fromCalendarAction(calendarAction: CalendarAction): CalendarDatePickerAction? =
            if (calendarAction !is CalendarAction.DatePicker) null
            else calendarAction.datePicker

            fun toCalendarAction(datePickerAction: CalendarDatePickerAction): CalendarAction =
            CalendarAction.DatePicker(datePickerAction)
    }
}

fun CalendarDatePickerAction.formatForDebug() =
    when (this) {
        is CalendarDatePickerAction.ExampleAction -> "Example Action"
    }