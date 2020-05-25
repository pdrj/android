package com.toggl.di

import android.content.Context
import com.toggl.TogglApplication
import com.toggl.architecture.DispatcherProvider
import com.toggl.architecture.StoreScopeProvider
import com.toggl.architecture.core.FlowStore
import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.Store
import com.toggl.calendar.common.domain.CalendarAction
import com.toggl.calendar.common.domain.CalendarState
import com.toggl.domain.AppAction
import com.toggl.domain.AppState
import com.toggl.domain.mappings.mapAppStateToCalendarState
import com.toggl.domain.mappings.mapAppStateToOnboardingState
import com.toggl.domain.mappings.mapAppStateToTimerState
import com.toggl.domain.mappings.mapCalendarActionToAppAction
import com.toggl.domain.mappings.mapOnboardingActionToAppAction
import com.toggl.domain.mappings.mapTimerActionToAppAction
import com.toggl.onboarding.domain.actions.OnboardingAction
import com.toggl.onboarding.domain.states.OnboardingState
import com.toggl.timer.common.domain.TimerAction
import com.toggl.timer.common.domain.TimerState
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@Module(includes = [AppModuleBinds::class])
class AppModule {

    @Provides
    fun provideContext(application: TogglApplication): Context = application.applicationContext

    @FlowPreview
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Provides
    @Singleton
    fun appStore(
        @ProvideLoggingReducer reducer: Reducer<AppState, AppAction>,
        dispatcherProvider: DispatcherProvider,
        storeScopeProvider: StoreScopeProvider
    ): Store<AppState, AppAction> {
        return FlowStore.create(
            initialState = AppState(),
            reducer = reducer,
            dispatcherProvider = dispatcherProvider,
            storeScopeProvider = storeScopeProvider
        )
    }

    @Provides
    @ExperimentalCoroutinesApi
    fun onboardingStore(store: Store<AppState, AppAction>): Store<OnboardingState, OnboardingAction> =
        store.view(
            mapToLocalState = ::mapAppStateToOnboardingState,
            mapToGlobalAction = ::mapOnboardingActionToAppAction
        )

    @Provides
    @ExperimentalCoroutinesApi
    fun timerStore(store: Store<AppState, AppAction>): Store<TimerState, TimerAction> =
        store.view(
            mapToLocalState = ::mapAppStateToTimerState,
            mapToGlobalAction = ::mapTimerActionToAppAction
        )

    @Provides
    @ExperimentalCoroutinesApi
    fun calendarStore(store: Store<AppState, AppAction>): Store<CalendarState, CalendarAction> =
        store.view(
            mapToLocalState = ::mapAppStateToCalendarState,
            mapToGlobalAction = ::mapCalendarActionToAppAction
        )

    @Provides
    @Singleton
    fun dispatcherProvider() =
        DispatcherProvider(
            io = Dispatchers.IO,
            computation = Dispatchers.Default,
            main = Dispatchers.Main
        )
}
