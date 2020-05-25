package com.toggl.calendar.contextualmenu.domain

import arrow.optics.optics
import com.toggl.calendar.common.domain.CalendarState
import com.toggl.calendar.common.domain.SelectedCalendarItem

@optics
data class ContextualMenuState(
    val selectedItem: SelectedCalendarItem?
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