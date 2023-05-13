package de.dbaelz.compcardero.ui.game

import cafe.adriel.voyager.core.model.coroutineScope
import de.dbaelz.compcardero.ui.BaseStateScreenModel
import de.dbaelz.compcardero.ui.game.GameScreenContract.Event
import de.dbaelz.compcardero.ui.game.GameScreenContract.Navigation
import de.dbaelz.compcardero.ui.game.GameScreenContract.State
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch


class GameScreenModel : BaseStateScreenModel<State, Event, Navigation>(State) {
    init {
        coroutineScope.launch {
            events.scan(state.value, ::reduce).collect(::updateState)
        }
    }

    private fun reduce(state: State, event: Event): State {
        return state
    }
}