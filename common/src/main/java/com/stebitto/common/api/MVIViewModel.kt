package com.stebitto.common.api

import kotlinx.coroutines.flow.StateFlow

interface MVIViewModel<T : State, U : Intent> {
    val state: StateFlow<T>
    fun dispatch(intent: U)
}

interface State

interface Intent