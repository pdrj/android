package com.toggl.timer.common.domain

import com.toggl.architecture.DispatcherProvider
import com.toggl.architecture.core.Effect
import com.toggl.models.domain.EditableTimeEntry
import com.toggl.repository.interfaces.StartTimeEntryResult
import com.toggl.repository.interfaces.TimeEntryRepository
import kotlinx.coroutines.withContext

class StartTimeEntryEffect<Action>(
    private val repository: TimeEntryRepository,
    private val timeEntry: EditableTimeEntry,
    private val dispatcherProvider: DispatcherProvider,
    private val mapFn: (StartTimeEntryResult) -> Action
) : Effect<Action> {
    override suspend fun execute(): Action? = withContext(dispatcherProvider.io) {
        repository
            .startTimeEntry(timeEntry.workspaceId, timeEntry.description)
            .run(mapFn)
    }
}
