package com.toggl.calendar.di

import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.Store
import com.toggl.architecture.core.combine
import com.toggl.architecture.core.pullback
import com.toggl.calendar.calendarday.domain.CalendarDayAction
import com.toggl.calendar.calendarday.domain.CalendarDayReducer
import com.toggl.calendar.calendarday.domain.CalendarDayState
import com.toggl.calendar.common.domain.CalendarAction
import com.toggl.calendar.common.domain.CalendarState
import com.toggl.calendar.contextualmenu.domain.ContextualMenuAction
import com.toggl.calendar.contextualmenu.domain.ContextualMenuReducer
import com.toggl.calendar.contextualmenu.domain.ContextualMenuState
import com.toggl.calendar.datepicker.domain.CalendarDatePickerAction
import com.toggl.calendar.datepicker.domain.CalendarDatePickerReducer
import com.toggl.calendar.datepicker.domain.CalendarDatePickerState
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

typealias CalendarReducer = Reducer<CalendarState, CalendarAction>

@Module(subcomponents = [CalendarComponent::class])
class CalendarModule {

    @ExperimentalCoroutinesApi
    @Provides
    internal fun calendarDayStore(store: Store<CalendarState, CalendarAction>): Store<CalendarDayState, CalendarDayAction> =
        store.view(
            mapToLocalState = CalendarDayState.Companion::fromCalendarState,
            mapToGlobalAction = CalendarDayAction.Companion::toCalendarAction
        )

    @ExperimentalCoroutinesApi
    @Provides
    internal fun datePickerStore(store: Store<CalendarState, CalendarAction>): Store<CalendarDatePickerState, CalendarDatePickerAction> =
        store.view(
            mapToLocalState = CalendarDatePickerState.Companion::fromCalendarState,
            mapToGlobalAction = CalendarDatePickerAction.Companion::toCalendarAction
        )

    @ExperimentalCoroutinesApi
    @Provides
    internal fun contextualMenuStore(store: Store<CalendarState, CalendarAction>): Store<ContextualMenuState, ContextualMenuAction> =
        store.view(
            mapToLocalState = ContextualMenuState.Companion::fromCalendarState,
            mapToGlobalAction = ContextualMenuAction.Companion::toCalendarAction
        )

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Provides
    @Singleton
    internal fun calendarReducer(
        calendarDayReducer: CalendarDayReducer,
        datePickerReducer: CalendarDatePickerReducer,
        contextualMenuReducer: ContextualMenuReducer
    ): CalendarReducer {

        return combine(
            calendarDayReducer.pullback(
                mapToLocalState = CalendarDayState.Companion::fromCalendarState,
                mapToLocalAction = CalendarDayAction.Companion::fromCalendarAction,
                mapToGlobalState = CalendarDayState.Companion::toCalendarState,
                mapToGlobalAction = CalendarDayAction.Companion::toCalendarAction
            ),
            datePickerReducer.pullback(
                mapToLocalState = CalendarDatePickerState.Companion::fromCalendarState,
                mapToLocalAction = CalendarDatePickerAction.Companion::fromCalendarAction,
                mapToGlobalState = CalendarDatePickerState.Companion::toCalendarState,
                mapToGlobalAction = CalendarDatePickerAction.Companion::toCalendarAction
            ),
            contextualMenuReducer.pullback(
                mapToLocalState = ContextualMenuState.Companion::fromCalendarState,
                mapToLocalAction = ContextualMenuAction.Companion::fromCalendarAction,
                mapToGlobalState = ContextualMenuState.Companion::toCalendarState,
                mapToGlobalAction = ContextualMenuAction.Companion::toCalendarAction
            )
        )
    }
}