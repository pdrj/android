package com.toggl.calendar.contextualmenu.ui

import androidx.lifecycle.ViewModel
import com.toggl.architecture.core.Store
import com.toggl.calendar.contextualmenu.domain.ContextualMenuAction
import com.toggl.calendar.contextualmenu.domain.ContextualMenuState
import javax.inject.Inject

class ContextualMenuStoreViewModel @Inject constructor(
    store: Store<ContextualMenuState, ContextualMenuAction>
) : ViewModel(), Store<ContextualMenuState, ContextualMenuAction> by store