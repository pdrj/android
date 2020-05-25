package com.toggl.domain.mappings

import com.toggl.calendar.domain.CalendarAction
import com.toggl.calendar.domain.CalendarState
import com.toggl.domain.AppAction
import com.toggl.domain.AppState

fun mapAppStateToCalendarState(appState: AppState): CalendarState =
    CalendarState(appState.tasks, null)

fun mapAppActionToCalendarAction(appAction: AppAction): CalendarAction? =
    if (appAction is AppAction.Calendar) appAction.calendar else null

fun mapCalendarStateToAppState(appState: AppState, calendarState: CalendarState): AppState =
    calendarState.run {
        appState.copy()
    }

fun mapCalendarActionToAppAction(timerAction: CalendarAction): AppAction =
    AppAction.Calendar(timerAction)
