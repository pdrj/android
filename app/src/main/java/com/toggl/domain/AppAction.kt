package com.toggl.domain

import com.toggl.calendar.common.domain.CalendarAction
import com.toggl.domain.loading.LoadingAction
import com.toggl.onboarding.domain.actions.OnboardingAction
import com.toggl.timer.common.domain.TimerAction

sealed class AppAction {
    class Loading(val loading: LoadingAction) : AppAction()
    class Onboarding(val onboarding: OnboardingAction) : AppAction()
    class Timer(val timer: TimerAction) : AppAction()
    class Calendar(val calendar: CalendarAction) : AppAction()
}
