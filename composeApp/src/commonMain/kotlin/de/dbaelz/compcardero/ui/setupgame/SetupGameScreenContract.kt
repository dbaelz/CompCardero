package de.dbaelz.compcardero.ui.setupgame

import de.dbaelz.compcardero.data.game.GameConfig
import de.dbaelz.compcardero.data.game.GameDeck
import dev.icerock.moko.resources.StringResource

interface SetupGameScreenContract {
    // TODO: Use TextContent (e.g. error text)
    sealed interface State {
        object Loading : State
        data class Content(val gameConfig: GameConfig) : State
    }

    sealed interface Event {
        data class Loaded(val gameConfig: GameConfig) : Event
        object BackClicked : Event
        data class StartGame(val gameConfig: GameConfig) : Event
    }

    sealed interface Navigation {
        data class Game(
            val playerName: String,
            val gameConfig: GameConfig,
            val gameDeck: GameDeck
        ) : Navigation

        object Back : Navigation
    }
}