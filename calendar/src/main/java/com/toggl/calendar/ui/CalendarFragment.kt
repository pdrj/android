package com.toggl.calendar.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.toggl.calendar.R
import com.toggl.calendar.di.CalendarComponentProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val store: CalendarStoreViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        (requireActivity().applicationContext as CalendarComponentProvider)
            .provideCalendarComponent().inject(this)
        super.onAttach(context)
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
