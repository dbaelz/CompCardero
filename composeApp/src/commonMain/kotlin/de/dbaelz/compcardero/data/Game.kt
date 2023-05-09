package de.dbaelz.compcardero.data

class Game(
    private val player1: Player,
    private val player2: Player,
    private val gameConfig: GameConfig = GameConfig()
) {
    private var activePlayer = player1
    private var inactivePlayer = player2

    init {
        player1.onGameStarted()
        player2.onGameStarted()
    }

    fun startGame() {
        while (determineWinner() != null) {
            activePlayer.startTurn()
            activePlayer.playCards()

            activePlayer = inactivePlayer.also { inactivePlayer = activePlayer }
        }

        // TODO: Communicate winner to UI
        println("Winner: ${determineWinner()}")
    }

    private fun determineWinner(): Player? {
        return when {
            activePlayer.health < 1 -> activePlayer
            inactivePlayer.health < 1 -> inactivePlayer
            else -> null
        }
    }
}

data class GameConfig(
    val startingHandSize: Int = 3,
    val maxHandSize: Int = 5,
    val startHealth: Int = 30,
    val startEnergy: Int = 0,
    val maxEnergy: Int = 10
)