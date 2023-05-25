package de.dbaelz.compcardero.ui.endgame

import cafe.adriel.voyager.core.model.coroutineScope
import de.dbaelz.compcardero.data.PlayerStats
import de.dbaelz.compcardero.ui.BaseStateScreenModel
import de.dbaelz.compcardero.ui.endgame.EndGameScreenContract.Event
import de.dbaelz.compcardero.ui.endgame.EndGameScreenContract.Navigation
import de.dbaelz.compcardero.ui.endgame.EndGameScreenContract.State
import dev.icerock.moko.resources.ImageResource
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch


class EndGameScreenModel(
    endScreenImageRes: ImageResource, winner: PlayerStats, loser: PlayerStats
) : BaseStateScreenModel<State, Event, Navigation>(State(endScreenImageRes, winner, loser)) {
    init {
        coroutineScope.launch {
            events.scan(state.value, ::reduce).collect(::updateState)
        }
    }

    private fun reduce(state: State, event: Event): State {
        return when (event) {
            Event.BackClicked -> {
                navigate(Navigation.Back)
                state
            }
        }
    }
}