package com.toggl.domain.mappings

import com.toggl.calendar.common.domain.CalendarAction
import com.toggl.calendar.common.domain.CalendarState
import com.toggl.domain.AppAction
import com.toggl.domain.AppState

fun mapAppStateToCalendarState(appState: AppState): CalendarState =
    CalendarState(
        appState.timeEntries,
        appState.calendarLocalState
    )

fun mapAppActionToCalendarAction(appAction: AppAction): CalendarAction? =
    if (appAction is AppAction.Calendar) appAction.calendar else null

fun mapCalendarStateToAppState(appState: AppState, calendarState: CalendarState): AppState =
    appState.copy(
        timeEntries = calendarState.timeEntries,
        calendarLocalState = calendarState.localState
    )

fun mapCalendarActionToAppAction(timerAction: CalendarAction): AppAction =
    AppAction.Calendar(timerAction)
