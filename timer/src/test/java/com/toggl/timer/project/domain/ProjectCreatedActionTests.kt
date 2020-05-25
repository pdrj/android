package com.toggl.timer.project.domain

import com.toggl.repository.interfaces.ProjectRepository
import com.toggl.timer.common.CoroutineTest
import com.toggl.timer.common.assertNoEffectsWereReturned
import com.toggl.models.domain.EditableProject
import com.toggl.timer.common.testReduce
import io.kotlintest.matchers.collections.shouldContain
import io.kotlintest.shouldBe
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
@DisplayName("The ProjectCreated action")
internal class ProjectCreatedActionTests : CoroutineTest() {
    private val repository = mockk<ProjectRepository>()
    private val reducer = ProjectReducer(repository, dispatcherProvider)
    private val listOfProjects = listOf(
        createProject(1, "Project 1", clientId = 1, workspaceId = 1),
        createProject(2, "Project 2", clientId = 1, workspaceId = 1),
        createProject(3, "Project 3", clientId = 1, workspaceId = 1)
    ).associateBy { it.id }

    @Test
    fun `adds the created project to the list of projects`() = runBlockingTest {

        val initialState = createInitialState(EditableProject.createValidBecauseClientsAreDifferent(), listOfProjects)
        val newProject = createProject(4, "Project 4")

        reducer.testReduce(
            initialState = initialState,
            action = ProjectAction.ProjectCreated(newProject)
        ) { state, _ ->
            state.projects.count() shouldBe (listOfProjects.size + 1)
            state.projects.values.shouldContain(newProject)
        }
    }

    @Test
    fun `returns no effects`() = runBlockingTest {

        val initialState = createInitialState(EditableProject.createValidBecauseClientsAreDifferent(), listOfProjects)
        val newProject = createProject(4, "Project 4")

        reducer.testReduce(
            initialState = initialState,
            action = ProjectAction.ProjectCreated(newProject),
            testCase = ::assertNoEffectsWereReturned
        )
    }
}