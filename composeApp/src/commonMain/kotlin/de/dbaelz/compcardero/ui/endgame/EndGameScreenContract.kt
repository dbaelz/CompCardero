package de.dbaelz.compcardero.ui.endgame

import de.dbaelz.compcardero.data.game.PlayerStats
import dev.icerock.moko.resources.ImageResource

interface EndGameScreenContract {
    data class State(
        val endScreenImageRes: ImageResource,
        val winner: PlayerStats,
        val loser: PlayerStats
    )

    sealed interface Event {
        object BackClicked : Event
    }

    sealed interface Navigation {
        object Back : Navigation
    }
}