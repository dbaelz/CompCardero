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
        deck = initialCardDeck.shuffled().take(gameConfig.deckSize).toMutableList(),
        hand = mutableListOf(),
        health = gameConfig.startHealth,
        energy = gameConfig.startEnergy,
        energySlots = gameConfig.startEnergy,
        gameConfig = gameConfig
    )

    val opponent = Player(
        name = opponentName,
        deck = initialCardDeck.shuffled().take(gameConfig.deckSize).toMutableList(),
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
    GameCard(1, 1, 0, 1, MR.images.card_placeholder),
    GameCard(2, 1, 0, 1, MR.images.card_placeholder),
    GameCard(3, 0, 1, 1, MR.images.card_placeholder),
    GameCard(4, 0, 1, 1, MR.images.card_placeholder),
    GameCard(5, 1, 1, 2, MR.images.card_placeholder),
    GameCard(6, 2, 0, 2, MR.images.card_placeholder),
    GameCard(7, 0, 2, 2, MR.images.card_placeholder),
    GameCard(8, 2, 1, 3, MR.images.card_placeholder),
    GameCard(9, 1, 2, 3, MR.images.card_placeholder),
    GameCard(10, 3, 1, 4, MR.images.card_placeholder),
    GameCard(11, 1, 3, 5, MR.images.card_placeholder),
    GameCard(12, 2, 2, 4, MR.images.card_placeholder),
    GameCard(13, 4, 0, 4, MR.images.card_placeholder),
    GameCard(14, 0, 4, 4, MR.images.card_placeholder),
    GameCard(15, 4, 1, 4, MR.images.card_placeholder),
    GameCard(16, 1, 4, 4, MR.images.card_placeholder),
    GameCard(17, 5, 0, 5, MR.images.card_placeholder),
    GameCard(18, 5, 1, 5, MR.images.card_placeholder),
    GameCard(19, 1, 5, 5, MR.images.card_placeholder),
    GameCard(20, 0, 6, 5, MR.images.card_placeholder),
    GameCard(21, 3, 3, 5, MR.images.card_placeholder),
    GameCard(22, 4, 3, 6, MR.images.card_placeholder),
    GameCard(23, 3, 4, 6, MR.images.card_placeholder),
    GameCard(24, 1, 6, 6, MR.images.card_placeholder),
)