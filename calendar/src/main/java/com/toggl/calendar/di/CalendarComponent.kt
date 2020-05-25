package com.toggl.calendar.di

import com.toggl.calendar.calendarday.ui.CalendarDayFragment
import com.toggl.calendar.contextualmenu.ui.ContextualMenuFragment
import com.toggl.calendar.datepicker.ui.CalendarDatePickerFragment
import dagger.Subcomponent

@Subcomponent
interface CalendarComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): CalendarComponent
    }

    fun inject(fragment: CalendarDayFragment)
    fun inject(fragment: CalendarDatePickerFragment)
    fun inject(fragment: ContextualMenuFragment)
}

interface CalendarComponentProvider {
    fun provideCalendarComponent(): CalendarComponent
}