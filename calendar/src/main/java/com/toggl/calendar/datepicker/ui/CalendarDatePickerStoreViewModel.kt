package com.toggl.calendar.datepicker.ui

import androidx.lifecycle.ViewModel
import com.toggl.architecture.core.Store
import com.toggl.calendar.datepicker.domain.CalendarDatePickerAction
import com.toggl.calendar.datepicker.domain.CalendarDatePickerState
import javax.inject.Inject

class CalendarDatePickerStoreViewModel @Inject constructor(
    store: Store<CalendarDatePickerState, CalendarDatePickerAction>
) : ViewModel(), Store<CalendarDatePickerState, CalendarDatePickerAction> by store