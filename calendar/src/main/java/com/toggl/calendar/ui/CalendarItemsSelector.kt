package com.toggl.calendar.ui

import com.toggl.environment.services.time.TimeService
import com.toggl.models.domain.TimeEntry
import org.threeten.bp.Duration
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.temporal.ChronoUnit
import kotlin.math.absoluteValue

fun calendarItemsSelector(
    timeService: TimeService,
    timeEntries: Map<Long, TimeEntry>,
    calendarEvents: List<CalendarItem.CalendarEvent>,
    date: OffsetDateTime
): List<CalendarItem> {
    return emptyList()
}