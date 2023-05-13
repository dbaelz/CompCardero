package de.dbaelz.compcardero.data

import de.dbaelz.compcardero.MR

class Game(
    private val player: Player,
    private val opponent: Player,
    gameConfig: GameConfig = GameConfig()
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
        val gameCard = opponent.hand
            .filter { it.energyCost <= opponent.energy }
            .minByOrNull { it.energyCost }
        if (gameCard != null) {
            val damageOpponent = opponent.playCard(gameCard)
            player.health -= damageOpponent
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
    val player = Player(
        name = playerName,
        deck = initialCardDeck.shuffled().toMutableList(),
        hand = mutableListOf(),
        health = gameConfig.startHealth,
        energy = gameConfig.startEnergy,
        energySlots = gameConfig.startEnergy,
        gameConfig = gameConfig
    )

    val opponent = Player(
        name = opponentName,
        deck = initialCardDeck.shuffled().toMutableList(),
        hand = mutableListOf(),
        health = gameConfig.startHealth,
        energy = gameConfig.startEnergy,
        energySlots = gameConfig.startEnergy,
        gameConfig = gameConfig
    )

    return Game(
        player = player,
        opponent = opponent,
        gameConfig = gameConfig
    )
}

private val initialCardDeck: MutableList<GameCard> = mutableListOf(
    GameCard(1, 1, 1, MR.images.card_placeholder),
    GameCard(2, 1, 1, MR.images.card_placeholder),
    GameCard(3, 1, 1, MR.images.card_placeholder),
    GameCard(4, 1, 1, MR.images.card_placeholder),
    GameCard(5, 2, 2, MR.images.card_placeholder),
    GameCard(6, 2, 2, MR.images.card_placeholder),
    GameCard(7, 3, 3, MR.images.card_placeholder),
    GameCard(8, 4, 3, MR.images.card_placeholder),
    GameCard(9, 5, 4, MR.images.card_placeholder),
    GameCard(10, 7, 5, MR.images.card_placeholder),
)