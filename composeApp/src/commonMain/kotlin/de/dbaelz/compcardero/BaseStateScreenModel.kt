package de.dbaelz.compcardero

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.update

abstract class BaseStateScreenModel<STATE, EVENT, NAVIGATION>(
    initialState: STATE
) : StateScreenModel<STATE>(initialState) {
    private val mutableEvents: MutableSharedFlow<EVENT> =
        MutableSharedFlow(extraBufferCapacity = Int.MAX_VALUE)
    val events: Flow<EVENT> = mutableEvents

    private val mutableNavigation: MutableSharedFlow<NAVIGATION> =
        MutableSharedFlow(extraBufferCapacity = Int.MAX_VALUE)
    val navigation: Flow<NAVIGATION> = mutableNavigation

    fun sendEvent(event: EVENT) {
        check(mutableEvents.tryEmit(event))
    }

    fun navigate(navigation: NAVIGATION) {
        check(mutableNavigation.tryEmit(navigation))
    }

    fun updateState(newState: STATE) {
        mutableState.update { newState }
    }

}