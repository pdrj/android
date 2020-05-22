package com.toggl.calendar.common

import com.toggl.environment.services.calendar.CalendarEvent
import com.toggl.models.domain.TimeEntry
import org.threeten.bp.Duration
import org.threeten.bp.OffsetDateTime

fun createTimeEntry(
    id: Long,
    description: String = "",
    startTime: OffsetDateTime = OffsetDateTime.now(),
    duration: Duration? = Duration.ofMinutes(2),
    billable: Boolean = false,
    projectId: Long? = null,
    workspaceId: Long = 1,
    taskId: Long? = null,
    tags: List<Long> = emptyList()
) =
    TimeEntry(
        id,
        description,
        startTime,
        duration,
        billable,
        workspaceId,
        projectId,
        taskId,
        false,
        tags
    )

fun createCalendarEvent(
    id: String = "",
    description: String = "",
    startTime: OffsetDateTime = OffsetDateTime.now(),
    duration: Duration = Duration.ofMinutes(2),
    color: String = "#c2c2c2",
    calendarId: String = ""
) = CalendarEvent(
    id,
    startTime,
    duration,
    description,
    color,
    calendarId
)