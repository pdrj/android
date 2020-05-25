package com.toggl.calendar.common.domain

import arrow.optics.optics
import com.toggl.models.domain.TimeEntry
import org.threeten.bp.OffsetDateTime

@optics
data class CalendarState(
    val timeEntries: Map<Long, TimeEntry>,
    val localState: LocalState
) {
    data class LocalState internal constructor(
        internal val selectedDate: OffsetDateTime,
        internal val selectedItem: SelectedCalendarItem?
    ) {
        constructor() : this(OffsetDateTime.now(), null)
    }

    companion object
}