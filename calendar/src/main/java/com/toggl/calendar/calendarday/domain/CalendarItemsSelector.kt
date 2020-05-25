package com.toggl.calendar.calendarday.domain

import com.toggl.architecture.core.Selector
import com.toggl.calendar.common.domain.CalendarItem
import org.threeten.bp.OffsetDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarItemsSelector @Inject constructor(
    private val calendarLayoutCalculator: CalendarLayoutCalculator
) : Selector<CalendarDayState, List<CalendarItem>> {

    override suspend fun select(state: CalendarDayState): List<CalendarItem> {
        val localDate = state.date.toLocalDate()
        fun isOnDate(startTime: OffsetDateTime, endTime: OffsetDateTime?) =
            startTime.toLocalDate() == localDate && (endTime == null || endTime.toLocalDate() == localDate)

        val filteredTimeEntries = state.timeEntries.filterValues { isOnDate(it.startTime, it.startTime + it.duration) }
        val filteredEvents = state.events.filterValues { isOnDate(it.startTime, it.startTime + it.duration) }
        return filteredTimeEntries
            .values.map { CalendarItem.TimeEntry(it) }
            .plus(filteredEvents.values.map { CalendarItem.CalendarEvent(it) })
            .run(calendarLayoutCalculator::calculateLayoutAttributes)
    }
}
