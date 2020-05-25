package com.toggl.timer.running.domain

import com.toggl.models.domain.TimeEntry
import com.toggl.models.domain.EditableTimeEntry

fun createInitialState(
    editableTimeEntry: EditableTimeEntry,
    timeEntries: Map<Long, TimeEntry> = mapOf()
) =
    RunningTimeEntryState(
        editableTimeEntry = editableTimeEntry,
        timeEntries = timeEntries
    )