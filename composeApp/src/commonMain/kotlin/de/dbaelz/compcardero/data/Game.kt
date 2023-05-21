package de.dbaelz.compcardero.data

import de.dbaelz.compcardero.decks.fantasyGameDeck
import dev.icerock.moko.resources.ImageResource

class Game(
    val deckCard: ImageResource,
    private val player: Player,
    private val opponent: Player,
    private val opponentStrategy: Strategy = RandomStrategy(),
    gameConfig: GameConfig = GameConfig(),
) {
    var isPlayerActive: Boolean = true
        private set

    init {
        player.drawInitialCards(gameConfig.startHandSize)
        opponent.drawInitialCards(gameConfig.startHandSize)
    }

    fun startOpponentTurn() {
        opponent.startTurn()
    }

    fun playOpponentCards() {
        while (true) {
            val gameCard =
                opponentStrategy.nextCard(opponent.hand, opponent.energy, opponent.energySlots)
            if (gameCard != null) {
                val damageOpponent = opponent.playCard(gameCard)
                player.health -= damageOpponent
            } else {
                break
            }
        }
        isPlayerActive = true
    }

    fun startTurn() {
        if (isPlayerActive) {
            player.startTurn()
        }
    }

    fun cardSelected(gameCard: GameCard) {
        if (isPlayerActive) {
            val damageOpponent = player.playCard(gameCard)
            opponent.health -= damageOpponent
        }
    }

    fun endTurnSelected() {
        isPlayerActive = false
    }

    fun determineWinner(): Pair<PlayerStats, PlayerStats>? {
        return when {
            player.health < 1 -> opponent.toStats() to player.toStats()
            opponent.health < 1 -> player.toStats() to opponent.toStats()
            else -> null
        }
    }

    fun getPlayerStats(): Pair<PlayerStats, PlayerStats> = player.toStats() to opponent.toStats()

    private fun Player.toStats(): PlayerStats = PlayerStats(
        name = this.name,
        numberDeckCards = this.deck.size,
        hand = this.hand,
        energy = this.energy,
        energySlots = this.energySlots,
        health = this.health
    )

}

fun createNewGame(
    playerName: String = "You",
    opponentName: String = "Opponent",
    gameConfig: GameConfig = GameConfig()
): Game {
    // TODO: Only temporary. Will be obsolete when player can choose cards from deck
    val initialDeck = fantasyGameDeck.cards + fantasyGameDeck.cards + fantasyGameDeck.cards

    val player = Player(
        name = playerName,
        deck = initialDeck.shuffled().take(gameConfig.deckSize).toMutableList(),
        hand = mutableListOf(),
        health = gameConfig.startHealth,
        energy = gameConfig.startEnergy,
        energySlots = gameConfig.startEnergy,
        gameConfig = gameConfig
    )

    val opponent = Player(
        name = opponentName,
        deck = initialDeck.shuffled().take(gameConfig.deckSize).toMutableList(),
        hand = mutableListOf(),
        health = gameConfig.startHealth,
        energy = gameConfig.startEnergy,
        energySlots = gameConfig.startEnergy,
        gameConfig = gameConfig
    )

    return Game(
        deckCard = fantasyGameDeck.deckCard,
        player = player,
        opponent = opponent,
        gameConfig = gameConfig
    )
}
