package com.toggl.calendar.calendarday.domain

import arrow.optics.optics
import com.toggl.calendar.common.domain.CalendarState
import com.toggl.models.domain.TimeEntry
import org.threeten.bp.OffsetDateTime

@optics
data class CalendarDayState(
    val timeEntries: Map<Long, TimeEntry>,
    val date: OffsetDateTime
) {
    companion object {
        fun fromCalendarState(calendarState: CalendarState) =
            CalendarDayState(
                calendarState.timeEntries,
                calendarState.localState.selectedDate
            )

        fun toCalendarState(calendarState: CalendarState, calendarDayState: CalendarDayState) =
            calendarState.copy(
                timeEntries = calendarDayState.timeEntries,
                localState = calendarState.localState.copy(
                    selectedDate = calendarDayState.date
                )
            )
    }
}