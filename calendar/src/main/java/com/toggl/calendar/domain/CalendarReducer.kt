package com.toggl.calendar.domain

import com.toggl.architecture.core.Effect
import com.toggl.architecture.core.MutableValue
import com.toggl.architecture.core.Reducer
import com.toggl.common.feature.extensions.mutateWithoutEffects
import javax.inject.Inject

class CalendarReducer @Inject constructor() : Reducer<CalendarState, CalendarAction> {

    override fun reduce(
        state: MutableValue<CalendarState>,
        action: CalendarAction
    ): List<Effect<CalendarAction>> =
        when (action) {
            is CalendarAction.ItemTapped -> state.mutateWithoutEffects {
                copy(selectedItem = action.calendarItem.toSelectedCalendarItem())
            }
        }
}
