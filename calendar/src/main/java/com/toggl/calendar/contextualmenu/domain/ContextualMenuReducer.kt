package com.toggl.calendar.contextualmenu.domain

import com.toggl.architecture.core.Effect
import com.toggl.architecture.core.MutableValue
import com.toggl.architecture.core.Reducer
import com.toggl.common.feature.extensions.mutateWithoutEffects
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContextualMenuReducer @Inject constructor() : Reducer<ContextualMenuState, ContextualMenuAction> {

    override fun reduce(
        state: MutableValue<ContextualMenuState>,
        action: ContextualMenuAction
    ): List<Effect<ContextualMenuAction>> =
        when (action) {
            is ContextualMenuAction.ExampleAction -> state.mutateWithoutEffects {
                copy()
            }
        }
}
