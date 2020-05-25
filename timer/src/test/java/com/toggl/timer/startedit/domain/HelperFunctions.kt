package com.toggl.timer.startedit.domain

import arrow.core.mapOf
import com.toggl.architecture.DispatcherProvider
import com.toggl.environment.services.time.TimeService
import com.toggl.models.common.AutocompleteSuggestion
import com.toggl.models.domain.Project
import com.toggl.models.domain.Tag
import com.toggl.models.domain.TimeEntry
import com.toggl.models.domain.Workspace
import com.toggl.repository.Repository
import com.toggl.timer.common.domain.EditableTimeEntry
import io.mockk.mockk

fun createInitialState(
    tags: List<Tag> = listOf(),
    projects: List<Project> = listOf(),
    workspaces: List<Workspace> = listOf(),
    timeEntries: List<TimeEntry> = listOf(),
    editableTimeEntry: EditableTimeEntry = EditableTimeEntry.empty(1),
    autoCompleteSuggestions: List<AutocompleteSuggestion> = listOf()
) =
    StartEditState(
        tags = tags.associateBy { it.id },
        tasks = mapOf(),
        clients = mapOf(),
        projects = projects.associateBy { it.id },
        workspaces = workspaces.associateBy { it.id },
        timeEntries = timeEntries.associateBy { it.id },
        editableTimeEntry = editableTimeEntry,
        autocompleteSuggestions = autoCompleteSuggestions,
        dateTimePickMode = DateTimePickMode.None,
        temporalInconsistency = TemporalInconsistency.None,
        cursorPosition = 0
    )

fun createReducer(
    repository: Repository = mockk(),
    timeService: TimeService = mockk(),
    dispatcherProvider: DispatcherProvider = mockk()
): StartEditReducer = StartEditReducer(repository, timeService, dispatcherProvider)