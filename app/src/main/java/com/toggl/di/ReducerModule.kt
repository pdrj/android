package com.toggl.di

import com.toggl.architecture.core.Reducer
import com.toggl.calendar.di.CalendarReducer
import com.toggl.domain.AppAction
import com.toggl.domain.AppState
import com.toggl.domain.reducers.AnalyticsReducer
import com.toggl.domain.loading.LoadingReducer
import com.toggl.domain.reducers.FeatureAvailabilityReducer
import com.toggl.domain.reducers.LoggingReducer
import com.toggl.domain.reducers.createAppReducer
import com.toggl.onboarding.domain.reducers.OnboardingReducer
import com.toggl.timer.common.domain.TimerReducer
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@Module
class ReducerModule {

    @Provides
    @Singleton
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @ProvideAppReducer
    fun appReducer(
        loadingReducer: LoadingReducer,
        onboardingReducer: OnboardingReducer,
        timerReducer: TimerReducer,
        calendarReducer: CalendarReducer,
        analyticsReducer: AnalyticsReducer
    ): Reducer<AppState, AppAction> =
        createAppReducer(
            loadingReducer,
            onboardingReducer,
            timerReducer,
            calendarReducer,
            analyticsReducer
        )

    @Provides
    @Singleton
    @ProvideFeatureAvailabilityReducer
    fun featureAvailabilityReducer(
        @ProvideAppReducer innerReducer: Reducer<AppState, AppAction>
    ): Reducer<AppState, AppAction> =
        FeatureAvailabilityReducer(innerReducer)

    @Provides
    @Singleton
    @ProvideLoggingReducer
    fun loggingReducer(
        @ProvideFeatureAvailabilityReducer innerReducer: Reducer<AppState, AppAction>
    ): Reducer<AppState, AppAction> =
        LoggingReducer(innerReducer)
}