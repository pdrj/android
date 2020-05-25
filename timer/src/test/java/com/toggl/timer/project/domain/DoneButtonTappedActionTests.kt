package com.toggl.timer.project.domain

import com.toggl.repository.interfaces.ProjectRepository
import com.toggl.timer.common.CoroutineTest
import com.toggl.timer.common.assertNoEffectsWereReturned
import com.toggl.models.domain.EditableProject
import com.toggl.timer.common.testReduce
import com.toggl.timer.exceptions.EditableProjectShouldNotBeNullException
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@ExperimentalCoroutinesApi
@DisplayName("The DoneButtonTapped action")
internal class DoneButtonTappedActionTests : CoroutineTest() {
    private val repository = mockk<ProjectRepository>()
    private val reducer = ProjectReducer(repository, dispatcherProvider)
    private val listOfProjects = listOf(
        createProject(1, "Project 1", clientId = 1, workspaceId = 1),
        createProject(2, "Project 2", clientId = 1, workspaceId = 1),
        createProject(3, "Project 3", clientId = 1, workspaceId = 1)
    ).associateBy { it.id }

    @ParameterizedTest
    @MethodSource("validProjects")
    fun `returns a create project effect when the project is valid`(validProject: EditableProject) = runBlockingTest {
        val initialState = createInitialState(
            editableProject = validProject,
            projects = listOfProjects
        )

        reducer.testReduce(
            initialState = initialState,
            action = ProjectAction.DoneButtonTapped
        ) { _, effects -> effects.single()::class shouldBe CreateProjectEffect::class }
    }

    @ParameterizedTest
    @MethodSource("validProjects")
    fun `sets the project to null when the project is valid`(validProject: EditableProject) = runBlockingTest {
        val initialState = createInitialState(
            editableProject = validProject,
            projects = listOfProjects
        )

        reducer.testReduce(
            initialState = initialState,
            action = ProjectAction.DoneButtonTapped
        ) { state, _ -> state.editableProject shouldBe null }
    }

    @Test
    fun `sets an error when the project is invalid`() = runBlockingTest {

        val initialState = createInitialState(
            editableProject = EditableProject.createInvalid(),
            projects = listOfProjects
        )

        reducer.testReduce(
            initialState,
            ProjectAction.DoneButtonTapped
        ) { state, _ -> state.editableProject!!.error shouldBe EditableProject.ProjectError.ProjectAlreadyExists }
    }

    @Test
    fun `returns no effects when the project is invalid`() = runBlockingTest {

        val initialState = createInitialState(
            editableProject = EditableProject.createInvalid(),
            projects = listOfProjects
        )

        reducer.testReduce(
            initialState = initialState,
            action = ProjectAction.DoneButtonTapped,
            testCase = ::assertNoEffectsWereReturned
        )
    }

    @Test
    fun `throws if the editableProject is null`() = runBlockingTest {

        val initialState = createInitialState(
            editableProject = null,
            projects = listOfProjects
        )

        shouldThrow<EditableProjectShouldNotBeNullException> {
            reducer.testReduce(
                initialState = initialState,
                action = ProjectAction.DoneButtonTapped,
                testCase = ::assertNoEffectsWereReturned
            )
        }
    }

    companion object {
        @JvmStatic
        fun validProjects(): List<EditableProject> = listOf(
            EditableProject.createValidBecauseWorkspacesAreDifferent(),
            EditableProject.createValidBecauseWorkspacesAndClientsAreDifferent(),
            EditableProject.createValidBecauseWorkspacesNamesAndClientsAreDifferent(),
            EditableProject.createValidBecauseNamesAreDifferent(),
            EditableProject.createValidBecauseNamesAndClientsAreDifferent(),
            EditableProject.createValidBecauseClientsAreDifferent()
        )
    }
}