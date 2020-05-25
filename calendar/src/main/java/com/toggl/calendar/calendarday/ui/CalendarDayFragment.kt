package com.toggl.calendar.calendarday.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.toggl.calendar.R
import com.toggl.calendar.di.CalendarComponentProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CalendarDayFragment : Fragment(R.layout.fragment_calendarday) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val store: CalendarDayStoreViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        (requireActivity().applicationContext as CalendarComponentProvider)
            .provideCalendarComponent().inject(this)

        super.onAttach(context)
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        store.state
            .onEach { _ -> }
            .launchIn(lifecycleScope)
    }
}
