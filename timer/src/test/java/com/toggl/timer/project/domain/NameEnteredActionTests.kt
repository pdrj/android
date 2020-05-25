package com.toggl.timer.project.domain

import com.toggl.timer.common.CoroutineTest
import com.toggl.timer.common.assertNoEffectsWereReturned
import com.toggl.models.domain.EditableProject
import com.toggl.timer.common.testReduce
import com.toggl.timer.common.toMutableValue
import com.toggl.timer.exceptions.EditableProjectShouldNotBeNullException
import io.kotlintest.shouldBe
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@ExperimentalCoroutinesApi
@DisplayName("The NameEntered action")
internal class NameEnteredActionTests : CoroutineTest() {
    val reducer = ProjectReducer(mockk(), dispatcherProvider)

    @Test
    fun `should update state with the new name`() = runBlockingTest {
        val initialState = ProjectState(EditableProject.empty(1), emptyMap())
        reducer.testReduce(
            initialState,
            ProjectAction.NameEntered("xxxy")
        ) { state, _ -> state.editableProject!!.name shouldBe "xxxy" }
    }

    @Test
    fun `should set the error to none`() = runBlockingTest {
        val projectWithError = EditableProject.empty(1).copy(error = EditableProject.ProjectError.ProjectAlreadyExists)
        val initialState = ProjectState(projectWithError, emptyMap())
        reducer.testReduce(
            initialState,
            ProjectAction.NameEntered("xxxy")
        ) { state, _ -> state.editableProject!!.error shouldBe EditableProject.ProjectError.None }
    }

    @Test
    fun `should produce no effects`() = runBlockingTest {
        val initialState = ProjectState(EditableProject.empty(1), emptyMap())
        reducer.testReduce(initialState, ProjectAction.NameEntered("xxxy"), ::assertNoEffectsWereReturned)
    }

    @Test
    fun `should throw when editableProject is null`() {
        var initialState = ProjectState(null, emptyMap())
        assertThrows<EditableProjectShouldNotBeNullException> {
            reducer.reduce(
                initialState.toMutableValue { initialState = it },
                ProjectAction.NameEntered("xxyy")
            )
        }
    }
}