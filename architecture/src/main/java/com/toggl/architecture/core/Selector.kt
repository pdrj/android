package com.toggl.architecture.core

interface Selector<Input, Output> {
    suspend fun select(state: Input) : Output
}