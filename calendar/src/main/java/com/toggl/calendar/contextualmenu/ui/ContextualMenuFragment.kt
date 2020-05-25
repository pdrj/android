package com.toggl.calendar.contextualmenu.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.toggl.calendar.R
import com.toggl.calendar.contextualmenu.domain.ContextualMenuAction
import com.toggl.calendar.di.CalendarComponentProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ContextualMenuFragment : Fragment(R.layout.fragment_contextualmenu) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val store: ContextualMenuStoreViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        (requireActivity().applicationContext as CalendarComponentProvider)
            .provideCalendarComponent().inject(this)

        super.onAttach(context)
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        store.dispatch(ContextualMenuAction.ExampleAction)

        store.state
            .onEach { _ -> }
            .launchIn(lifecycleScope)
    }
}
