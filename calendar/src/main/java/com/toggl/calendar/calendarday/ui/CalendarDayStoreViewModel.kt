package com.toggl.calendar.calendarday.ui

import androidx.lifecycle.ViewModel
import com.toggl.architecture.core.Store
import com.toggl.calendar.calendarday.domain.CalendarDayAction
import com.toggl.calendar.calendarday.domain.CalendarDayState
import javax.inject.Inject

class CalendarDayStoreViewModel @Inject constructor(
    store: Store<CalendarDayState, CalendarDayAction>
) : ViewModel(), Store<CalendarDayState, CalendarDayAction> by store