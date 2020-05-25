package com.toggl.timer.project.domain

import com.toggl.models.domain.Project
import com.toggl.models.domain.Task
import com.toggl.models.domain.EditableProject

fun createInitialState(
    editableProject: EditableProject? = null,
    projects: Map<Long, Project> = mapOf()
) = ProjectState(
    editableProject = editableProject,
    projects = projects
)

fun createProject(
    id: Long,
    name: String = "Project",
    color: String = "#1e1e1e",
    active: Boolean = true,
    isPrivate: Boolean = false,
    billable: Boolean? = null,
    workspaceId: Long = 1,
    clientId: Long? = null
) = Project(
    id,
    name,
    color,
    active,
    isPrivate,
    billable,
    workspaceId,
    clientId
)

fun createTask(
    id: Long,
    name: String = "Task",
    active: Boolean = true,
    projectId: Long = 1,
    workspaceId: Long = 1,
    userId: Long? = null
) = Task(
    id,
    name,
    active,
    projectId,
    workspaceId,
    userId
)

fun EditableProject.Companion.createInvalid() =
    EditableProject(
        name = "Project 1",
        color = "#123123",
        active = true,
        isPrivate = true,
        billable = null,
        workspaceId = 1,
        clientId = 1
    )

fun EditableProject.Companion.createValidBecauseWorkspacesAreDifferent() =
    EditableProject(
        name = "Project 1",
        color = "#123123",
        active = true,
        isPrivate = true,
        billable = null,
        workspaceId = 2,
        clientId = 1
    )

fun EditableProject.Companion.createValidBecauseWorkspacesAndClientsAreDifferent() =
    EditableProject(
        name = "Project 1",
        color = "#123123",
        active = true,
        isPrivate = true,
        billable = null,
        workspaceId = 2,
        clientId = 2
    )

fun EditableProject.Companion.createValidBecauseWorkspacesNamesAndClientsAreDifferent() =
    EditableProject(
        name = "Project 4",
        color = "#123123",
        active = true,
        isPrivate = true,
        billable = null,
        workspaceId = 2,
        clientId = 2
    )

fun EditableProject.Companion.createValidBecauseNamesAreDifferent() =
    EditableProject(
        name = "Project 4",
        color = "#123123",
        active = true,
        isPrivate = true,
        billable = null,
        workspaceId = 1,
        clientId = 1
    )

fun EditableProject.Companion.createValidBecauseNamesAndClientsAreDifferent() =
    EditableProject(
        name = "Project 4",
        color = "#123123",
        active = true,
        isPrivate = true,
        billable = null,
        workspaceId = 1,
        clientId = 2
    )

fun EditableProject.Companion.createValidBecauseClientsAreDifferent() =
    EditableProject(
        name = "Project 1",
        color = "#123123",
        active = true,
        isPrivate = true,
        billable = null,
        workspaceId = 1,
        clientId = 2
    )
