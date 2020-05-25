package com.toggl.calendar.domain

import arrow.optics.optics
import com.toggl.models.domain.Task

@optics
data class CalendarState(
    val tasks: Map<Long, Task>,
    val selectedItem: SelectedCalendarItem?
) {
    companion object
}