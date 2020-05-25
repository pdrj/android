package com.toggl.calendar.common.domain

import arrow.optics.optics
import com.toggl.calendar.calendarday.domain.CalendarDayAction
import com.toggl.calendar.calendarday.domain.formatForDebug
import com.toggl.calendar.contextualmenu.domain.ContextualMenuAction
import com.toggl.calendar.contextualmenu.domain.formatForDebug
import com.toggl.calendar.datepicker.domain.CalendarDatePickerAction
import com.toggl.calendar.datepicker.domain.formatForDebug

@optics
sealed class CalendarAction {
    class CalendarDay(val calendarDay: CalendarDayAction) : CalendarAction()
    class DatePicker(val datePicker: CalendarDatePickerAction) : CalendarAction()
    class ContextualMenu(val contextualMenu: ContextualMenuAction) : CalendarAction()

    companion object
}

fun CalendarAction.formatForDebug() =
    when (this) {
        is CalendarAction.CalendarDay -> calendarDay.formatForDebug()
        is CalendarAction.DatePicker -> datePicker.formatForDebug()
        is CalendarAction.ContextualMenu -> contextualMenu.formatForDebug()
    }