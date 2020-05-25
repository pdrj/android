package com.toggl.calendar.calendarday.domain

import com.toggl.architecture.core.Effect
import com.toggl.architecture.core.MutableValue
import com.toggl.architecture.core.Reducer
import com.toggl.common.feature.extensions.mutateWithoutEffects
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarDayReducer @Inject constructor() : Reducer<CalendarDayState, CalendarDayAction> {

    override fun reduce(
        state: MutableValue<CalendarDayState>,
        action: CalendarDayAction
    ): List<Effect<CalendarDayAction>> =
        when (action) {
            is CalendarDayAction.ExampleAction -> state.mutateWithoutEffects {
                copy()
            }
        }
}
