package com.toggl.domain.reducers

import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.combine
import com.toggl.architecture.core.pullback
import com.toggl.calendar.di.CalendarReducer
import com.toggl.domain.AppAction
import com.toggl.domain.AppState
import com.toggl.domain.loading.LoadingReducer
import com.toggl.domain.mappings.mapAppActionToCalendarAction
import com.toggl.domain.mappings.mapAppActionToLoadingAction
import com.toggl.domain.mappings.mapAppActionToOnboardingAction
import com.toggl.domain.mappings.mapAppActionToTimerAction
import com.toggl.domain.mappings.mapAppStateToCalendarState
import com.toggl.domain.mappings.mapAppStateToLoadingState
import com.toggl.domain.mappings.mapAppStateToOnboardingState
import com.toggl.domain.mappings.mapAppStateToTimerState
import com.toggl.domain.mappings.mapCalendarActionToAppAction
import com.toggl.domain.mappings.mapCalendarStateToAppState
import com.toggl.domain.mappings.mapLoadingActionToAppAction
import com.toggl.domain.mappings.mapLoadingStateToAppState
import com.toggl.domain.mappings.mapOnboardingActionToAppAction
import com.toggl.domain.mappings.mapOnboardingStateToAppState
import com.toggl.domain.mappings.mapTimerActionToAppAction
import com.toggl.domain.mappings.mapTimerStateToAppState
import com.toggl.onboarding.domain.reducers.OnboardingReducer
import com.toggl.timer.common.domain.TimerReducer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

typealias AppReducer = Reducer<AppState, AppAction>

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun createAppReducer(
    loadingReducer: LoadingReducer,
    onboardingReducer: OnboardingReducer,
    timerReducer: TimerReducer,
    calendarReducer: CalendarReducer,
    analyticsReducer: AnalyticsReducer
): AppReducer =
    combine(
        analyticsReducer,
        loadingReducer.pullback(
            mapToLocalState = ::mapAppStateToLoadingState,
            mapToLocalAction = ::mapAppActionToLoadingAction,
            mapToGlobalState = ::mapLoadingStateToAppState,
            mapToGlobalAction = ::mapLoadingActionToAppAction
        ),
        timerReducer.pullback(
            mapToLocalState = ::mapAppStateToTimerState,
            mapToLocalAction = ::mapAppActionToTimerAction,
            mapToGlobalState = ::mapTimerStateToAppState,
            mapToGlobalAction = ::mapTimerActionToAppAction
        ),
        onboardingReducer.pullback(
            mapToLocalState = ::mapAppStateToOnboardingState,
            mapToLocalAction = ::mapAppActionToOnboardingAction,
            mapToGlobalState = ::mapOnboardingStateToAppState,
            mapToGlobalAction = ::mapOnboardingActionToAppAction
        ),
        calendarReducer.pullback(
            mapToLocalState = ::mapAppStateToCalendarState,
            mapToLocalAction = ::mapAppActionToCalendarAction,
            mapToGlobalState = ::mapCalendarStateToAppState,
            mapToGlobalAction = ::mapCalendarActionToAppAction
        )
    )
