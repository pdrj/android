package com.toggl.calendar.calendarday.domain

import com.toggl.calendar.common.createTimeEntry
import com.toggl.calendar.common.domain.CalendarItem
import com.toggl.calendar.common.domain.SelectedCalendarItem
import com.toggl.calendar.common.testReduceNoEffects
import com.toggl.calendar.common.testReduceState
import com.toggl.environment.services.calendar.CalendarEvent
import com.toggl.models.domain.EditableTimeEntry
import io.kotlintest.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.threeten.bp.Duration
import org.threeten.bp.OffsetDateTime

@ExperimentalCoroutinesApi
@DisplayName("The ItemTappedActionTests action")
internal class ItemTappedActionTests {

    private val state = CalendarDayState(mapOf(), null, OffsetDateTime.now())
    private val reducer = CalendarDayReducer()

    private val timeEntry = createTimeEntry(1)
    private val calendarEvent = CalendarEvent("1", OffsetDateTime.now(), Duration.ofSeconds(10), "", "", "")

    private val timeEntryItemToBeSelected = CalendarItem.TimeEntry(timeEntry)
    private val calendarEventItemToBeSelected = CalendarItem.CalendarEvent(calendarEvent)

    @Test
    fun `should set selectedItem correctly when timeEntry is tapped`() = runBlockingTest {
        reducer.testReduceState(
            state,
            CalendarDayAction.ItemTapped(timeEntryItemToBeSelected)
        ) { state ->
            state shouldBe state.copy(selectedItem = SelectedCalendarItem.SelectedTimeEntry(EditableTimeEntry.fromSingle(timeEntry)))
        }
    }

    @Test
    fun `should set selectedItem correctly when calendarEvent is tapped`() = runBlockingTest {
        reducer.testReduceState(
            state,
            CalendarDayAction.ItemTapped(calendarEventItemToBeSelected)
        ) { state ->
            state shouldBe state.copy(selectedItem = SelectedCalendarItem.SelectedCalendarEvent(calendarEvent))
        }
    }

    @Test
    fun `shouldn't return any effect`() = runBlockingTest {
        reducer.testReduceNoEffects(state, CalendarDayAction.ItemTapped(timeEntryItemToBeSelected))
        reducer.testReduceNoEffects(state, CalendarDayAction.ItemTapped(calendarEventItemToBeSelected))
    }
}