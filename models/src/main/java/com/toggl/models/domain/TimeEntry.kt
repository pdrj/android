package com.toggl.models.domain

import java.time.Duration
import java.time.OffsetDateTime

data class TimeEntry(
    val id: Long = 0,
    val description: String,
    val startTime: OffsetDateTime,
    val duration: Duration?,
    val billable: Boolean,
    val workspaceId: Long,
    val projectId: Long?,
    val taskId: Long?,
    val isDeleted: Boolean,
    val tagIds: List<Long>
)
