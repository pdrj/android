package com.toggl.calendar.calendarday.domain

import com.toggl.calendar.common.domain.CalendarItem
import com.toggl.environment.services.calendar.CalendarEvent
import com.toggl.models.domain.TimeEntry
import org.threeten.bp.OffsetDateTime

fun calendarItemsSelector(
    calendarLayoutCalculator: CalendarLayoutCalculator,
    timeEntries: Map<Long, TimeEntry>,
    calendarEvents: List<CalendarEvent>,
    date: OffsetDateTime
): List<CalendarItem> {
    val localDate = date.toLocalDate()
    fun isOnDate(startTime: OffsetDateTime, endTime: OffsetDateTime?) =
        startTime.toLocalDate() == localDate && (endTime == null || endTime.toLocalDate() == localDate)

    val filteredTimeEntries = timeEntries.filterValues { isOnDate(it.startTime, it.startTime + it.duration) }
    val filteredEvents = calendarEvents.filter { isOnDate(it.startTime, it.startTime + it.duration) }
    return filteredTimeEntries
        .values.map { CalendarItem.TimeEntry(it) }
        .plus(filteredEvents.map { CalendarItem.CalendarEvent(it) })
        .run(calendarLayoutCalculator::calculateLayoutAttributes)
}
