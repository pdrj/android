package com.toggl.timer.startedit.domain

import com.toggl.timer.common.assertNoEffectsWereReturned
import com.toggl.models.domain.EditableTimeEntry
import com.toggl.timer.common.testReduce
import com.toggl.timer.common.testReduceException
import com.toggl.timer.exceptions.EditableTimeEntryShouldNotBeNullException
import io.kotlintest.shouldBe
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@kotlinx.coroutines.ExperimentalCoroutinesApi
@DisplayName("The AddTagChipTapped action")
internal class AddTagChipTappedTests {
    val initialState = createInitialState()
    val reducer = createReducer()

    @Test
    fun `should throw if editableTimeEntry is null`() {
        reducer.testReduceException(
            initialState.copy(editableTimeEntry = null),
            StartEditAction.TagButtonTapped,
            EditableTimeEntryShouldNotBeNullException::class.java
        )
    }

    @Test
    fun `should set the description to # if it was empty and return no effects`() = runBlockingTest {
        reducer.testReduce(initialState, StartEditAction.TagButtonTapped) { state, effects ->
            state.editableTimeEntry!!.description shouldBe "#"
            assertNoEffectsWereReturned(state, effects)
        }
    }

    @Test
    fun `should append # to description and return no effects`() = runBlockingTest {
        val editableWithDescription =
            EditableTimeEntry(workspaceId = 1, description = "asdf")

        reducer.testReduce(initialState.copy(editableTimeEntry = editableWithDescription), StartEditAction.TagButtonTapped) { state, effects ->
            state.editableTimeEntry!!.description shouldBe editableWithDescription.description + " #"
            assertNoEffectsWereReturned(state, effects)
        }
    }
}
