package de.dbaelz.compcardero.data.game

import de.dbaelz.compcardero.decks.fantasyGameDeck
import dev.icerock.moko.resources.ImageResource
import kotlinx.serialization.Serializable

@Serializable
data class GameConfig(
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

data class GameDeck(
    val name: String,
    val deckCard: ImageResource,
    val endScreenImageRes: ImageResource,
    val cards: List<GameCard>
)

data class GameCard(
    val id: String,
    val attack: Int,
    val heal: Int,
    val energyCost: Int,
    val imageResource: ImageResource
)

data class PlayerStats(
    val name: String,
    val numberDeckCards: Int,
    val hand: List<GameCard>,
    val energy: Int,
    val energySlots: Int,
    val health: Int
)
