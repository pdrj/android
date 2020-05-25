package com.toggl.calendar.datepicker.domain

import arrow.optics.optics
import com.toggl.calendar.common.domain.CalendarState
import org.threeten.bp.OffsetDateTime

@optics
data class CalendarDatePickerState(
    val date: OffsetDateTime
) {
    companion object {
        fun fromCalendarState(calendarState: CalendarState) =
            CalendarDatePickerState(calendarState.localState.selectedDate)

        fun toCalendarState(calendarState: CalendarState, datePickerState: CalendarDatePickerState) =
            calendarState.copy(
                localState = calendarState.localState.copy(selectedDate = datePickerState.date)
            )
    }
}