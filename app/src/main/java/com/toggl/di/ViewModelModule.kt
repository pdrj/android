package com.toggl.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.toggl.calendar.calendarday.ui.CalendarDayStoreViewModel
import com.toggl.calendar.contextualmenu.ui.ContextualMenuStoreViewModel
import com.toggl.calendar.datepicker.ui.CalendarDatePickerStoreViewModel
import com.toggl.onboarding.ui.LoginViewModel
import com.toggl.timer.log.ui.TimeEntriesLogStoreViewModel
import com.toggl.timer.project.ui.ProjectStoreViewModel
import com.toggl.timer.running.ui.RunningTimeEntryStoreViewModel
import com.toggl.timer.startedit.ui.StartEditStoreViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TimeEntriesLogStoreViewModel::class)
    abstract fun bindTimeEntriesLogViewModel(viewModel: TimeEntriesLogStoreViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StartEditStoreViewModel::class)
    abstract fun bindStartTimeEntryViewModel(viewModel: StartEditStoreViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RunningTimeEntryStoreViewModel::class)
    abstract fun bindRunningTimeEntryStoreViewModel(viewModel: RunningTimeEntryStoreViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProjectStoreViewModel::class)
    abstract fun bindProjectStoreViewModel(viewModel: ProjectStoreViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CalendarDayStoreViewModel::class)
    abstract fun bindCalendarDayStoreViewModel(viewModel: CalendarDayStoreViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CalendarDatePickerStoreViewModel::class)
    abstract fun bindCalendarDatePickerStoreViewModel(viewModel: CalendarDatePickerStoreViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContextualMenuStoreViewModel::class)
    abstract fun bindContextualMenuStoreViewModel(viewModel: ContextualMenuStoreViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: TogglViewModelFactory): ViewModelProvider.Factory
}
