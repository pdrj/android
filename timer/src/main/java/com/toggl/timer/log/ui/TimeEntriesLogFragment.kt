package com.toggl.timer.log.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.toggl.architecture.extensions.select
import com.toggl.common.Constants.timeEntryDeletionDelayMs
import com.toggl.common.deepLinks
import com.toggl.common.performClickHapticFeedback
import com.toggl.environment.services.time.TimeService
import com.toggl.models.common.SwipeDirection
import com.toggl.timer.R
import com.toggl.timer.di.TimerComponentProvider
import com.toggl.timer.log.domain.FlatTimeEntryViewModel
import com.toggl.timer.log.domain.TimeEntriesLogAction
import com.toggl.timer.log.domain.TimeEntriesLogSelector
import com.toggl.timer.log.domain.TimeEntryGroupViewModel
import com.toggl.timer.log.domain.TimeEntryViewModel
import kotlinx.android.synthetic.main.fragment_time_entries_log.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

class TimeEntriesLogFragment : Fragment(R.layout.fragment_time_entries_log) {
    @Inject
    lateinit var timeService: TimeService

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var timeEntriesLogSelector: TimeEntriesLogSelector

    private val store: TimeEntriesLogStoreViewModel by viewModels { viewModelFactory }

    private val adapter = TimeEntriesLogAdapter(
        { store.dispatch(TimeEntriesLogAction.TimeEntryTapped(it)) },
        { store.dispatch(TimeEntriesLogAction.TimeEntryGroupTapped(it)) },
        { store.dispatch(TimeEntriesLogAction.ContinueButtonTapped(it)) },
        { store.dispatch(TimeEntriesLogAction.ToggleTimeEntryGroupTapped(it)) }
    )

    private var snackbar: Snackbar? = null

    override fun onAttach(context: Context) {
        (requireActivity().applicationContext as TimerComponentProvider)
            .provideTimerComponent().inject(this)
        super.onAttach(context)
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.adapter = adapter
        val swipeCallback = createSwipeActionCallback(requireContext())
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(recycler_view)

        lifecycleScope.launch {
            store.select(timeEntriesLogSelector)
                .distinctUntilChanged()
                .onEach { adapter.submitList(it) }
                .launchIn(this)

            store.state
                .map { it.editableTimeEntry != null }
                .distinctUntilChanged()
                .onEach { isEditViewExpanded ->
                    if (isEditViewExpanded) {
                        this@TimeEntriesLogFragment.context?.performClickHapticFeedback()
                        findNavController().navigate(deepLinks.timeEntriesStartEditDialog)
                    }
                }
                .launchIn(this)

            store.state
                .map { it.entriesPendingDeletion }
                .distinctUntilChanged()
                .onEach { showUndoDeletionSnackbar(it) }
                .launchIn(this)
        }
    }

    override fun onDestroyView() {
        snackbar = null
        super.onDestroyView()
    }

    private fun showUndoDeletionSnackbar(idsToDelete: Set<Long>) {
        snackbar?.dismiss()

        if (idsToDelete.isEmpty())
            return

        val deletionMessage = resources.getQuantityString(R.plurals.entriesDeleted, idsToDelete.size, idsToDelete.size)
        snackbar = Snackbar.make(coordinator_layout, deletionMessage, timeEntryDeletionDelayMs.toInt()).apply {
            anchorView = running_time_entry_layout
            setAction(getString(R.string.undo).toUpperCase(Locale.getDefault())) {
                store.dispatch(TimeEntriesLogAction.UndoButtonTapped)
            }
            show()
        }
    }

    private fun onLogItemSwiped(adapterPosition: Int, swipeDirection: SwipeDirection) {
        val currentItems: List<TimeEntryViewModel> = adapter.currentList
        if (adapterPosition >= currentItems.size) {
            adapter.notifyDataSetChanged()
            return
        }

        when (val item = currentItems[adapterPosition]) {
            is FlatTimeEntryViewModel -> TimeEntriesLogAction.TimeEntrySwiped(item.id, swipeDirection)
            is TimeEntryGroupViewModel -> TimeEntriesLogAction.TimeEntryGroupSwiped(item.timeEntryIds, swipeDirection)
            else -> null
        }?.let(store::dispatch)

        if (swipeDirection == SwipeDirection.Right) {
            adapter.notifyItemChanged(adapterPosition)
        }
    }

    private fun createSwipeActionCallback(context: Context): SwipeActionCallback {
        val leftSwipeColor = ContextCompat.getColor(context, R.color.stop_time_entry_button_background)
        val rightSwipeColor = ContextCompat.getColor(context, R.color.start_time_entry_button_background)
        val actionTextColor = ContextCompat.getColor(context, R.color.text_on_surface)
        val actionFontSize = resources.getDimension(R.dimen.swipe_action_font_size)
        val actionPadding = resources.getDimension(R.dimen.swipe_action_text_padding)

        val swipeLeftParams = SwipeActionParams(
            text = getString(R.string.delete),
            backgroundColor = leftSwipeColor,
            textColor = actionTextColor,
            fontSize = actionFontSize,
            textPadding = actionPadding
        )
        val swipeRightParams = SwipeActionParams(
            text = getString(R.string.continue_this),
            backgroundColor = rightSwipeColor,
            textColor = actionTextColor,
            fontSize = actionFontSize,
            textPadding = actionPadding
        )

        return SwipeActionCallback(
            swipeLeftParams = swipeLeftParams,
            swipeRightParams = swipeRightParams,
            onSwipeActionListener = ::onLogItemSwiped
        )
    }
}
