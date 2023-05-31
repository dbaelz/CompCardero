package de.dbaelz.compcardero.ui.setupgame

import de.dbaelz.compcardero.data.game.GameConfig
import de.dbaelz.compcardero.data.game.GameDeck
import dev.icerock.moko.resources.StringResource

interface SetupGameScreenContract {
    data class State(
        val deckSize: TextContent<Int>,
        val startHandSize: TextContent<Int>,
        val maxCardDrawPerTurn: TextContent<Int>,
        val maxHandSize: TextContent<Int>,
        val startHealth: TextContent<Int>,
        val startEnergy: TextContent<Int>,
        val energyPerTurn: TextContent<Int>,
        val energySlotsPerTurn: TextContent<Int>,
        val maxEnergySlots: TextContent<Int>,
        val gameDeckNames: List<String>
    )

    sealed interface Event {
        object BackClicked : Event
        data class StartGame(val setupGameConfiguration: SetupGameConfiguration) : Event
    }

    sealed interface Navigation {
        data class Game(
            val playerName: String,
            val gameConfig: GameConfig,
            val gameDeck: GameDeck
        ) : Navigation

        object Back : Navigation
    }

    data class TextContent<VALUE>(val value: VALUE, val error: StringResource? = null)
}