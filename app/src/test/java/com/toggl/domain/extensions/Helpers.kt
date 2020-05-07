package com.toggl.domain.extensions

import com.toggl.models.domain.Client
import com.toggl.models.domain.Project
import com.toggl.models.domain.Tag
import com.toggl.models.domain.Task
import com.toggl.models.domain.TimeEntry
import java.time.Duration
import java.time.OffsetDateTime

fun createTimeEntry(
    id: Long,
    description: String = "",
    startTime: OffsetDateTime = OffsetDateTime.now(),
    duration: Duration? = null,
    billable: Boolean = false,
    projectId: Long? = null
) =
    TimeEntry(
        id,
        description,
        startTime,
        duration,
        billable,
        1,
        projectId,
        null,
        false,
        emptyList()
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

fun createClient(id: Long) = Client(id, "name: $id", 1)

fun createTag(id: Long) = Tag(id, "# $id", 1)

fun createTask(
    id: Long,
    projectId: Long = 1,
    workspaceId: Long = 1,
    name: String = "Task $id",
    active: Boolean = true,
    userId: Long? = null
) = Task(
    id,
    name,
    active,
    projectId,
    workspaceId,
    userId
)