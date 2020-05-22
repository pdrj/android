package com.toggl.calendar.ui

import com.toggl.environment.services.calendar.CalendarEvent
import com.toggl.models.domain.TimeEntry
import org.threeten.bp.OffsetDateTime

fun calendarItemsSelector(
    timeEntries: Map<Long, TimeEntry>,
    calendarEvents: List<CalendarEvent>,
    date: OffsetDateTime
): List<CalendarItem> {
    val localDate = date.toLocalDate()
    return timeEntries.values.map { CalendarItem.TimeEntry(it) }
        .plus(calendarEvents.map { CalendarItem.CalendarEvent(it) })
        .filter {
            val endTime = it.endTime()
            it.startTime().toLocalDate() == localDate && (endTime == null || endTime.toLocalDate() == localDate)
        }
}