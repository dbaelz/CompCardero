package de.dbaelz.compcardero.data

class Player(private val name: String, private val gameConfig: GameConfig) {
    var health: Int = gameConfig.startHealth
    var energySlots: Int = gameConfig.startEnergy
    var energy: Int = gameConfig.startEnergy
    val deck: MutableList<Card> = mutableListOf()
    val hand: MutableList<Card> = mutableListOf()

    fun onGameStarted() {
        repeat(gameConfig.startingHandSize) {
            drawCard()
        }
    }

    fun drawCard() {
        if (deck.isNotEmpty()) {
            val card = hand.random()
            deck.remove(card)

            if (hand.size < gameConfig.maxHandSize) {
                hand.add(card)
            } else {
                // TODO: Add communication for "card was dropped"
            }
        }
    }

    fun startTurn() {
        energySlots++
        energy = energySlots
        drawCard()
    }

    fun playCards(): Int {
        // TODO: Add basic implementation to play cards and return the damage done
        //  To animate/display it in UI we should return every damage done separate
        return 0
    }
}