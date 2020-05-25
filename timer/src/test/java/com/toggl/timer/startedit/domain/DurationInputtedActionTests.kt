package com.toggl.timer.startedit.domain

import com.toggl.common.Constants.TimeEntry.maxDurationInHours
import com.toggl.environment.services.time.TimeService
import com.toggl.timer.common.assertNoEffectsWereReturned
import com.toggl.models.domain.EditableTimeEntry
import com.toggl.timer.common.testReduce
import com.toggl.timer.common.testReduceState
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.converter.SimpleArgumentConverter
import org.junit.jupiter.params.provider.ValueSource
import org.threeten.bp.Duration
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset

@ExperimentalCoroutinesApi
@DisplayName("The DurationInputted action")
internal class DurationInputtedActionTests {
    val initialState = createInitialState()
    val timeService = mockk<TimeService>()
    val now = OffsetDateTime.of(2020, 1, 1, 10, 0, 0, 0, ZoneOffset.UTC)
    val reducer = createReducer(timeService = timeService)

    init {
        every { timeService.now() } returns now
    }

    private fun createEditableTimeEntry(startTime: OffsetDateTime?, duration: Duration?) =
        EditableTimeEntry.empty(1).copy(startTime = startTime, duration = duration)

    @Test
    fun `sets the duration inputted to the editableTimeEntry's startTime when the time entry is new`() = runBlockingTest {
        val initialTimeEntry = createEditableTimeEntry(null, null)
        val durationInputted = Duration.ofMinutes(30)
        val expectedStartTime = now - durationInputted
        val initialState = initialState.copy(editableTimeEntry = initialTimeEntry)

        reducer.testReduceState(
            initialState,
            action = StartEditAction.DurationInputted(durationInputted)
        ) {
            it shouldBe initialState.copy(
                editableTimeEntry = initialTimeEntry.copy(
                    startTime = expectedStartTime,
                    duration = null
                )
            )
        }
    }

    @Test
    fun `sets the duration inputted to the editableTimeEntry's startTime when the time entry is running`() = runBlockingTest {
        val initialTimeEntry = createEditableTimeEntry(now - Duration.ofSeconds(10), null)
        val durationInputted = Duration.ofMinutes(30)
        val expectedStartTime = now - durationInputted
        val initialState = initialState.copy(editableTimeEntry = initialTimeEntry)

        reducer.testReduceState(
            initialState,
            action = StartEditAction.DurationInputted(durationInputted)
        ) {
            it shouldBe initialState.copy(
                editableTimeEntry = initialTimeEntry.copy(
                    startTime = expectedStartTime,
                    duration = null
                )
            )
        }
    }

    @Test
    fun `sets the duration inputted to the editableTimeEntry's duration when the time entry is stopped`() = runBlockingTest {
        val initialStartTime = now - Duration.ofHours(1)
        val initialDuration = Duration.ofMinutes(30)
        val initialTimeEntry = createEditableTimeEntry(initialStartTime, initialDuration)
        val durationInputted = Duration.ofHours(1).plusMinutes(30)
        val initialState = initialState.copy(editableTimeEntry = initialTimeEntry)

        reducer.testReduceState(
            initialState,
            action = StartEditAction.DurationInputted(durationInputted)
        ) {
            it shouldBe initialState.copy(
                editableTimeEntry = initialTimeEntry.copy(
                    startTime = initialStartTime,
                    duration = durationInputted
                )
            )
        }
    }

    @Test
    fun `does not change anything when a negative duration is inputted`() = runBlockingTest {
        val initialStartTime = now - Duration.ofHours(1)
        val initialDuration = Duration.ofMinutes(30)
        val initialTimeEntry = createEditableTimeEntry(initialStartTime, initialDuration)
        val durationInputted = Duration.ofSeconds(-1)
        val initialState = initialState.copy(editableTimeEntry = initialTimeEntry)

        reducer.testReduceState(
            initialState,
            action = StartEditAction.DurationInputted(durationInputted)
        ) {
            it shouldBe initialState
        }
    }

    @Test
    fun `does not change anything when a duration longer than 999 hours is inputted`() = runBlockingTest {
        val initialStartTime = now - Duration.ofHours(1)
        val initialDuration = Duration.ofMinutes(30)
        val initialTimeEntry = createEditableTimeEntry(initialStartTime, initialDuration)
        val durationInputted = Duration.ofHours(maxDurationInHours).plusSeconds(1)
        val initialState = initialState.copy(editableTimeEntry = initialTimeEntry)

        reducer.testReduceState(
            initialState,
            action = StartEditAction.DurationInputted(durationInputted)
        ) {
            it shouldBe initialState
        }
    }

    @ParameterizedTest
    @ValueSource(longs = [0L, 999L])
    fun `does not dispatch any effects when valid durations are inputted`(@ConvertWith(LongToDuration::class) durationInputted: Duration) =
        runBlockingTest {
            val initialStartTime = now - Duration.ofHours(1)
            val initialDuration = Duration.ofMinutes(30)
            val initialTimeEntry = createEditableTimeEntry(initialStartTime, initialDuration)

            reducer.testReduce(
                initialState.copy(editableTimeEntry = initialTimeEntry),
                action = StartEditAction.DurationInputted(durationInputted),
                testCase = ::assertNoEffectsWereReturned
            )
        }

    @ParameterizedTest
    @ValueSource(longs = [-1L, 1000L])
    fun `does not dispatch any effects when invalid durations are inputted`(@ConvertWith(LongToDuration::class) durationInputted: Duration) =
        runBlockingTest {
            val initialStartTime = now - Duration.ofHours(1)
            val initialDuration = Duration.ofMinutes(30)
            val initialTimeEntry = createEditableTimeEntry(initialStartTime, initialDuration)

            reducer.testReduce(
                initialState.copy(editableTimeEntry = initialTimeEntry),
                action = StartEditAction.DurationInputted(durationInputted),
                testCase = ::assertNoEffectsWereReturned
            )
        }

    class LongToDuration : SimpleArgumentConverter() {
        override fun convert(source: Any?, targetType: Class<*>?): Any =
            Duration.ofHours(source as Long)
    }
}
