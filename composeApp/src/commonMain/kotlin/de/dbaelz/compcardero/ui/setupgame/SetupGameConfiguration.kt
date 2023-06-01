package de.dbaelz.compcardero.ui.setupgame

import de.dbaelz.compcardero.decks.fantasyGameDeck
import kotlinx.serialization.Serializable

@Serializable
data class SetupGameConfiguration(
    var playerName: String = "Player",
    var gameDeckNames: List<String> = listOf(fantasyGameDeck.name),
    var deckSize: Int = 24,
    var startHandSize: Int = 3,
    var maxCardDrawPerTurn: Int = 1,
    var maxHandSize: Int = 5,
    var startHealth: Int = 20,
    var startEnergy: Int = 3,
    var energyPerTurn: Int = 2,
    var energySlotsPerTurn: Int = 1,
    var maxEnergySlots: Int = 12,
    var gameDeckSelected: String = gameDeckNames.first()
)