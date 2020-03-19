package com.toggl.timer.common.domain

import arrow.optics.optics
import com.toggl.models.domain.Project
import com.toggl.models.domain.TimeEntry
import com.toggl.models.domain.Workspace

@optics
data class TimerState(
    val timeEntries: Map<Long, TimeEntry>,
    val projects: Map<Long, Project>,
    val workspaces: Map<Long, Workspace>,
    val localState: LocalState
) {
    data class LocalState internal constructor(
        internal val editedDescription: String,
        internal val editedTimeEntry: TimeEntry?
    ) {
        constructor() : this("", null)

        companion object
    }

    companion object
}