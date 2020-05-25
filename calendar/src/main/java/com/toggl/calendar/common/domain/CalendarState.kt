package com.toggl.calendar.common.domain

import arrow.core.Either
import arrow.optics.optics
import com.toggl.environment.services.calendar.CalendarEvent
import com.toggl.models.domain.TimeEntry
import org.threeten.bp.OffsetDateTime

@optics
data class CalendarState(
    val timeEntries: Map<Long, TimeEntry>,
    val localState: LocalState
) {
    data class LocalState internal constructor(
        internal val selectedDate: OffsetDateTime,
        internal val selectedItem: Either<TimeEntry, CalendarEvent>?
    ) {
        constructor() : this(OffsetDateTime.now(), null)
    }

    companion object
}