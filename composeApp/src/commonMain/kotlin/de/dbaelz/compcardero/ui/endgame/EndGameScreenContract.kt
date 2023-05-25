package de.dbaelz.compcardero.ui.endgame

import de.dbaelz.compcardero.data.PlayerStats

class EndGameScreenContract {
    data class State(val winner: PlayerStats, val loser: PlayerStats)

    sealed interface Event {
        object BackClicked : Event
    }

    sealed interface Navigation {
        object Back : Navigation
    }
}