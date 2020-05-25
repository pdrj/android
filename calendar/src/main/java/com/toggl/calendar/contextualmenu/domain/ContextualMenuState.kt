package com.toggl.calendar.contextualmenu.domain

import arrow.core.Either
import arrow.optics.optics
import com.toggl.calendar.common.domain.CalendarState
import com.toggl.environment.services.calendar.CalendarEvent
import com.toggl.models.domain.TimeEntry

@optics
data class ContextualMenuState(
    val selectedItem: Either<TimeEntry, CalendarEvent>?
) {
    companion object {
        fun fromCalendarState(calendarState: CalendarState) =
            ContextualMenuState(calendarState.localState.selectedItem)

        fun toCalendarState(calendarState: CalendarState, contextualMenuState: ContextualMenuState) =
            calendarState.copy(
                localState = calendarState.localState.copy(
                    selectedItem = contextualMenuState.selectedItem
                )
            )
    }
}