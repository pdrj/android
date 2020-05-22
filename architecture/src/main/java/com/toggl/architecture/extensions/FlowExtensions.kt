package com.toggl.architecture.extensions

import com.toggl.architecture.core.Selector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun <T> Flow<T>.merge(otherFlow: Flow<T>): Flow<T> = flow {
    emitAll(this@merge)
    emitAll(otherFlow)
}

@FlowPreview
fun <T> Flow<T>.emitIf(predicate: (T) -> Boolean): Flow<Unit> =
    flatMapConcat { if (predicate(it)) flowOf(Unit) else emptyFlow() }

fun <TInput, TOutput> Flow<TInput>.select(selector: Selector<TInput, TOutput>): Flow<TOutput> =
    this.map(selector::select)