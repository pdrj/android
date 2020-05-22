package com.toggl.architecture.extensions

import com.toggl.architecture.core.Selector
import com.toggl.architecture.core.Store
import kotlinx.coroutines.flow.Flow

fun <State, MappedState> Store<State, *>.select(selector: Selector<State, MappedState>): Flow<MappedState> =
    state.select(selector)