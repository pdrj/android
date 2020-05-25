package com.toggl.calendar.domain

sealed class CalendarAction {
    data class ItemTapped(val calendarItem: CalendarItem) : CalendarAction()

    companion object
}

fun CalendarAction.formatForDebug() =
    when (this) {
        is CalendarAction.ItemTapped -> "Calendar item tapped: $calendarItem"
    }