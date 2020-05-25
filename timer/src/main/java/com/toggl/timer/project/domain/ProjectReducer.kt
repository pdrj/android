package com.toggl.timer.project.domain

import com.toggl.common.feature.extensions.mutateWithoutEffects
import com.toggl.architecture.DispatcherProvider
import com.toggl.architecture.core.Effect
import com.toggl.architecture.core.MutableValue
import com.toggl.architecture.extensions.effect
import com.toggl.architecture.core.Reducer
import com.toggl.common.feature.extensions.returnEffect
import com.toggl.repository.interfaces.ProjectRepository
import com.toggl.models.domain.EditableProject
import com.toggl.models.domain.isValid
import com.toggl.repository.extensions.toDto
import com.toggl.timer.exceptions.EditableProjectShouldNotBeNullException
import javax.inject.Inject

class ProjectReducer @Inject constructor(
    private val repository: ProjectRepository,
    private val dispatcherProvider: DispatcherProvider
) : Reducer<ProjectState, ProjectAction> {

    override fun reduce(
        state: MutableValue<ProjectState>,
        action: ProjectAction
    ): List<Effect<ProjectAction>> =
        when (action) {
            is ProjectAction.NameEntered -> state.mutateWithoutEffects {
                if (editableProject == null) throw EditableProjectShouldNotBeNullException()
                ProjectState.editableProject.modify(this) {
                    it.copy(name = action.name, error = EditableProject.ProjectError.None)
                }
            }
            ProjectAction.DoneButtonTapped -> {
                val (project, projectCanBeCreated) = state.mapState {
                    val editableProject = editableProject ?: throw EditableProjectShouldNotBeNullException()
                    val listOfProjects = projects.values
                    editableProject to editableProject.isValid(listOfProjects)
                }

                if (projectCanBeCreated) state.mutate { copy(editableProject = null) } returnEffect createProject(project)
                else state.mutateWithoutEffects {
                    ProjectState.editableProject.modify(this) {
                        it.copy(error = EditableProject.ProjectError.ProjectAlreadyExists)
                    }
                }
            }
            is ProjectAction.ProjectCreated ->
                state.mutateWithoutEffects { copy(projects = projects + (action.project.id to action.project)) }
            ProjectAction.PrivateProjectSwitchTapped ->
                state.mutateWithoutEffects {
                    ProjectState.editableProject.modify(this) {
                        it.copy(isPrivate = !it.isPrivate)
                    }
                }
        }

    private fun createProject(editableProject: EditableProject) = effect(
        CreateProjectEffect(editableProject.toDto(), repository, dispatcherProvider)
    )
}
