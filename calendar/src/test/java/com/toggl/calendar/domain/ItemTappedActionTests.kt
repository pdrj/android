package com.toggl.calendar.domain

import com.toggl.calendar.common.createTimeEntry
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

    private val state = CalendarState(tasks = emptyMap(), selectedItem = null)
    private val reducer = CalendarReducer()

    private val timeEntry = createTimeEntry(1)
    private val calendarEvent = CalendarEvent("1", OffsetDateTime.now(), Duration.ofSeconds(10), "", "", "")

    private val timeEntryItemToBeSelected = CalendarItem.TimeEntry(timeEntry)
    private val calendarEventItemToBeSelected = CalendarItem.CalendarEvent(calendarEvent)

    @Test
    fun `should set selectedItem correctly when timeEntry is tapped`() = runBlockingTest {
        reducer.testReduceState(
            state,
            CalendarAction.ItemTapped(timeEntryItemToBeSelected)
        ) { state ->
            state shouldBe state.copy(selectedItem = SelectedCalendarItem.SelectedTimeEntry(EditableTimeEntry.fromSingle(timeEntry)))
        }
    }

    @Test
    fun `should set selectedItem correctly when calendarEvent is tapped`() = runBlockingTest {
        reducer.testReduceState(
            state,
            CalendarAction.ItemTapped(calendarEventItemToBeSelected)
        ) { state ->
            state shouldBe state.copy(selectedItem = SelectedCalendarItem.SelectedCalendarEvent(calendarEvent))
        }
    }

    @Test
    fun `shouldn't return any effect`() = runBlockingTest {
        reducer.testReduceNoEffects(state, CalendarAction.ItemTapped(timeEntryItemToBeSelected))
        reducer.testReduceNoEffects(state, CalendarAction.ItemTapped(calendarEventItemToBeSelected))
    }
}