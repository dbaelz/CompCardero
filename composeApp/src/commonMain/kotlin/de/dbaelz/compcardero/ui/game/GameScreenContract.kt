package de.dbaelz.compcardero.ui.game

import de.dbaelz.compcardero.data.Game
import de.dbaelz.compcardero.data.GameCard
import de.dbaelz.compcardero.data.PlayerStats

class GameScreenContract {
    sealed interface State {
        object Loading : State
        data class Game(
            val player: PlayerStats,
            val opponent: PlayerStats,
            val isPlayerActive: Boolean
        ) : State
    }


    sealed interface Event {
        data class GameInitialized(val game: Game) : Event

        data class CardSelected(val gameCard: GameCard) : Event

        object EndTurnClicked : Event

        object TurnEnded : Event

        object OpponentTurnEnded : Event
    }

    sealed interface Navigation {
        data class EndGame(val winner: PlayerStats, val loser: PlayerStats) : Navigation
    }
}