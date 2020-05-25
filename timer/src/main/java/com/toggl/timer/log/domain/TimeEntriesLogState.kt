package com.toggl.timer.log.domain

import arrow.optics.optics
import com.toggl.models.domain.Client
import com.toggl.models.domain.Project
import com.toggl.models.domain.TimeEntry
import com.toggl.models.domain.EditableTimeEntry
import com.toggl.timer.common.domain.TimerState

@optics
data class TimeEntriesLogState(
    val timeEntries: Map<Long, TimeEntry>,
    val projects: Map<Long, Project>,
    val clients: Map<Long, Client>,
    val expandedGroupIds: Set<Long>,
    val editableTimeEntry: EditableTimeEntry?,
    val entriesPendingDeletion: Set<Long>
) {
    companion object {
        fun fromTimerState(timerState: TimerState) =
            TimeEntriesLogState(
                timerState.timeEntries,
                timerState.projects,
                timerState.clients,
                timerState.localState.expandedGroupIds,
                timerState.localState.editableTimeEntry,
                timerState.localState.entriesPendingDeletion
            )

        fun toTimerState(timerState: TimerState, timeEntriesLogState: TimeEntriesLogState) =
            timerState.copy(
                timeEntries = timeEntriesLogState.timeEntries,
                localState = timerState.localState.copy(
                    editableTimeEntry = timeEntriesLogState.editableTimeEntry,
                    expandedGroupIds = timeEntriesLogState.expandedGroupIds,
                    entriesPendingDeletion = timeEntriesLogState.entriesPendingDeletion
                )
            )
    }
}
