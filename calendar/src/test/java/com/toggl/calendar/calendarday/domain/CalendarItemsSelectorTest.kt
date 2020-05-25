package com.toggl.calendar.calendarday.domain

import com.toggl.calendar.common.createCalendarEvent
import com.toggl.calendar.common.createTimeEntry
import com.toggl.calendar.common.domain.CalendarItem
import com.toggl.environment.services.time.TimeService
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.threeten.bp.Duration
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset

@DisplayName("The calendarItemsSelector")
internal class CalendarItemsSelectorTest {

    private val timeService: TimeService = mockk()
    private val calendarLayoutCalculator =
        CalendarLayoutCalculator(timeService)

    init {
        every { timeService.now() } returns OffsetDateTime.now()
    }

    @Nested
    @DisplayName("when selecting time entries only")
    inner class TimeEntriesOnly {
        @Test
        fun `selects only time entries from the specified date`() {
            val date = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
            val expectedTimeEntries = (1..10L).map {
                createTimeEntry(
                    it,
                    startTime = date.plusHours(1),
                    duration = Duration.ofMinutes(10),
                    description = "Expected"
                )
            }
            val notExpectedTimeEntries = (11..20L).map {
                createTimeEntry(
                    it,
                    startTime = date.plusDays(if (it % 2 == 0L) 1 else -1),
                    duration = Duration.ofMinutes(10),
                    description = "Unexpected"
                )
            }

            val selectedTimeEntries = calendarItemsSelector(
                calendarLayoutCalculator,
                (expectedTimeEntries + notExpectedTimeEntries).associateBy { it.id },
                emptyList(),
                date
            )

            assertThat(selectedTimeEntries).size().isEqualTo(expectedTimeEntries.size)
            assertThat(selectedTimeEntries).allMatch { it is CalendarItem.TimeEntry && it.timeEntry.description == "Expected" }
        }

        @Test
        fun `returns an empty list when there are no time entries on the specified date`() {
            val date = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
            val notExpectedTimeEntries = (11..20L).map {
                createTimeEntry(
                    it,
                    startTime = date.plusDays(if (it % 2 == 0L) 1 else -1),
                    duration = Duration.ofMinutes(10),
                    description = "Unexpected"
                )
            }

            val selectedTimeEntries = calendarItemsSelector(
                calendarLayoutCalculator,
                notExpectedTimeEntries.associateBy { it.id },
                emptyList(),
                date
            )

            assertThat(selectedTimeEntries).size().isZero
        }

        @Test
        fun `selects only time entries that start and end on the specified date`() {
            val date = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
            val expectedTimeEntries = (1..10L).map {
                createTimeEntry(
                    it,
                    startTime = date.plusHours(1),
                    duration = Duration.ofMinutes(10),
                    description = "Expected"
                )
            }
            val notExpectedTimeEntriesThatStartTheDayBefore = (11..20L).map {
                createTimeEntry(
                    it,
                    startTime = date.minusHours(1),
                    duration = Duration.ofHours(2),
                    description = "Unexpected"
                )
            }
            val notExpectedTimeEntriesThatEndTheDayAfter = (21..30L).map {
                createTimeEntry(
                    it,
                    startTime = date.plusHours(23),
                    duration = Duration.ofHours(2),
                    description = "Unexpected"
                )
            }

            val selectedTimeEntries = calendarItemsSelector(
                calendarLayoutCalculator,
                (expectedTimeEntries + notExpectedTimeEntriesThatStartTheDayBefore + notExpectedTimeEntriesThatEndTheDayAfter).associateBy { it.id },
                emptyList(),
                date
            )

            assertThat(selectedTimeEntries).size().isEqualTo(expectedTimeEntries.size)
            assertThat(selectedTimeEntries).allMatch { it is CalendarItem.TimeEntry && it.timeEntry.description == "Expected" }
        }
    }

    @Nested
    @DisplayName("when selecting calendar events only")
    inner class CalendarEventsOnly {
        @Test
        fun `selects only events from the specified date`() {
            val date = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
            val expectedEvents = (1..10L).map {
                createCalendarEvent(
                    startTime = date.plusHours(1),
                    duration = Duration.ofMinutes(10),
                    description = "Expected"
                )
            }
            val notExpectedEvents = (11..20L).map {
                createCalendarEvent(
                    startTime = date.plusDays(if (it % 2 == 0L) 1 else -1),
                    duration = Duration.ofMinutes(10),
                    description = "Unexpected"
                )
            }

            val selectedTimeEntries = calendarItemsSelector(
                calendarLayoutCalculator,
                emptyMap(),
                expectedEvents + notExpectedEvents,
                date
            )

            assertThat(selectedTimeEntries).size().isEqualTo(expectedEvents.size)
            assertThat(selectedTimeEntries).allMatch { it is CalendarItem.CalendarEvent && it.calendarEvent.description == "Expected" }
        }

        @Test
        fun `returns an empty list when there are no events on the specified date`() {
            val date = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
            val notExpectedEvents = (11..20L).map {
                createCalendarEvent(
                    startTime = date.plusDays(if (it % 2 == 0L) 1 else -1),
                    duration = Duration.ofMinutes(10),
                    description = "Unexpected"
                )
            }

            val selectedTimeEntries = calendarItemsSelector(
                calendarLayoutCalculator,
                emptyMap(),
                notExpectedEvents,
                date
            )

            assertThat(selectedTimeEntries).size().isZero
        }

        @Test
        fun `selects only events that start and end on the specified date`() {
            val date = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
            val expectedEvents = (1..10L).map {
                createCalendarEvent(
                    startTime = date.plusHours(1),
                    duration = Duration.ofMinutes(10),
                    description = "Expected"
                )
            }
            val notExpectedEventsThatStartTheDayBefore = (11..20L).map {
                createCalendarEvent(
                    startTime = date.minusHours(1),
                    duration = Duration.ofHours(2),
                    description = "Unexpected"
                )
            }
            val notExpectedEventsThatEndTheDayAfter = (21..30L).map {
                createCalendarEvent(
                    startTime = date.plusHours(23),
                    duration = Duration.ofHours(2),
                    description = "Unexpected"
                )
            }

            val selectedTimeEntries = calendarItemsSelector(
                calendarLayoutCalculator,
                emptyMap(),
                expectedEvents + notExpectedEventsThatStartTheDayBefore + notExpectedEventsThatEndTheDayAfter,
                date
            )

            assertThat(selectedTimeEntries).size().isEqualTo(expectedEvents.size)
            assertThat(selectedTimeEntries).allMatch { it is CalendarItem.CalendarEvent && it.calendarEvent.description == "Expected" }
        }
    }

    @Nested
    @DisplayName("when selecting time entries & calendar events")
    inner class MixAndMatch {
        @Test
        fun `selects only time entries & events from the specified date`() {
            val date = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
            val expectedTimeEntries = (1..10L).map {
                createTimeEntry(
                    it,
                    startTime = date.plusHours(1),
                    duration = Duration.ofMinutes(10),
                    description = "Expected"
                )
            }
            val expectedEvents = (1..10L).map {
                createCalendarEvent(
                    startTime = date.plusHours(1),
                    duration = Duration.ofMinutes(10),
                    description = "Expected"
                )
            }
            val notExpectedTimeEntries = (11..20L).map {
                createTimeEntry(
                    it,
                    startTime = date.plusDays(if (it % 2 == 0L) 1 else -1),
                    duration = Duration.ofMinutes(10),
                    description = "Unexpected"
                )
            }
            val notExpectedEvents = (11..20L).map {
                createCalendarEvent(
                    startTime = date.plusDays(if (it % 2 == 0L) 1 else -1),
                    duration = Duration.ofMinutes(10),
                    description = "Unexpected"
                )
            }

            val selectedTimeEntries = calendarItemsSelector(
                calendarLayoutCalculator,
                (expectedTimeEntries + notExpectedTimeEntries).associateBy { it.id },
                expectedEvents + notExpectedEvents,
                date
            )

            assertThat(selectedTimeEntries).size().isEqualTo(expectedTimeEntries.size + expectedEvents.size)
            assertThat(selectedTimeEntries).allMatch {
                when (it) {
                    is CalendarItem.TimeEntry -> it.timeEntry.description == "Expected"
                    is CalendarItem.CalendarEvent -> it.calendarEvent.description == "Expected"
                }
            }
        }

        @Test
        fun `returns an empty list when there are no time entries or events on the specified date`() {
            val date = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
            val notExpectedTimeEntries = (11..20L).map {
                createTimeEntry(
                    it,
                    startTime = date.plusDays(if (it % 2 == 0L) 1 else -1),
                    duration = Duration.ofMinutes(10),
                    description = "Unexpected"
                )
            }
            val notExpectedEvents = (11..20L).map {
                createCalendarEvent(
                    startTime = date.plusDays(if (it % 2 == 0L) 1 else -1),
                    duration = Duration.ofMinutes(10),
                    description = "Unexpected"
                )
            }

            val selectedTimeEntries = calendarItemsSelector(
                calendarLayoutCalculator,
                notExpectedTimeEntries.associateBy { it.id },
                notExpectedEvents,
                date
            )

            assertThat(selectedTimeEntries).size().isZero
        }

        @Test
        fun `selects only time entries & events that start and end on the specified date`() {
            val date = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
            val expectedTimeEntries = (1..10L).map {
                createTimeEntry(
                    it,
                    startTime = date.plusHours(1),
                    duration = Duration.ofMinutes(10),
                    description = "Expected"
                )
            }
            val expectedEvents = (1..10L).map {
                createCalendarEvent(
                    startTime = date.plusHours(1),
                    duration = Duration.ofMinutes(10),
                    description = "Expected"
                )
            }
            val notExpectedTimeEntriesThatStartTheDayBefore = (11..20L).map {
                createTimeEntry(
                    it,
                    startTime = date.minusHours(1),
                    duration = Duration.ofHours(2),
                    description = "Unexpected"
                )
            }
            val notExpectedEventsThatStartTheDayBefore = (11..20L).map {
                createCalendarEvent(
                    startTime = date.minusHours(1),
                    duration = Duration.ofHours(2),
                    description = "Unexpected"
                )
            }
            val notExpectedTimeEntriesThatEndTheDayAfter = (21..30L).map {
                createTimeEntry(
                    it,
                    startTime = date.plusHours(23),
                    duration = Duration.ofHours(2),
                    description = "Unexpected"
                )
            }
            val notExpectedEventsThatEndTheDayAfter = (21..30L).map {
                createCalendarEvent(
                    startTime = date.plusHours(23),
                    duration = Duration.ofHours(2),
                    description = "Unexpected"
                )
            }

            val selectedTimeEntries = calendarItemsSelector(
                calendarLayoutCalculator,
                (expectedTimeEntries + notExpectedTimeEntriesThatStartTheDayBefore + notExpectedTimeEntriesThatEndTheDayAfter).associateBy { it.id },
                expectedEvents + notExpectedEventsThatEndTheDayAfter + notExpectedEventsThatStartTheDayBefore,
                date
            )

            assertThat(selectedTimeEntries).size().isEqualTo(expectedTimeEntries.size + expectedEvents.size)
            assertThat(selectedTimeEntries).allMatch {
                when (it) {
                    is CalendarItem.TimeEntry -> it.timeEntry.description == "Expected"
                    is CalendarItem.CalendarEvent -> it.calendarEvent.description == "Expected"
                }
            }
        }
    }
}