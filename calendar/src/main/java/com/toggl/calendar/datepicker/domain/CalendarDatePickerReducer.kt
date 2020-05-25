package com.toggl.calendar.datepicker.domain

import com.toggl.architecture.core.Effect
import com.toggl.architecture.core.MutableValue
import com.toggl.architecture.core.Reducer
import com.toggl.common.feature.extensions.mutateWithoutEffects
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarDatePickerReducer @Inject constructor() : Reducer<CalendarDatePickerState, CalendarDatePickerAction> {

    override fun reduce(
        state: MutableValue<CalendarDatePickerState>,
        action: CalendarDatePickerAction
    ): List<Effect<CalendarDatePickerAction>> =
        when (action) {
            is CalendarDatePickerAction.ExampleAction -> state.mutateWithoutEffects {
                copy()
            }
        }
}
