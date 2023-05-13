package de.dbaelz.compcardero.data

import kotlin.math.min

data class Player(
    val name: String,
    var deck: MutableList<GameCard>,
    var hand: MutableList<GameCard>,
    var health: Int,
    var energy: Int,
    var energySlots: Int,
    private val gameConfig: GameConfig
) {
    fun startTurn() {
        energySlots = min(energySlots + gameConfig.energySlotsPerTurn, gameConfig.maxEnergySlots)
        energy = min(energy + gameConfig.energyPerTurn, energySlots)
        drawCards(gameConfig.maxHandSize - hand.size)
    }

    fun drawInitialCards(startHandSize: Int) {
        drawCards(startHandSize)
    }

    private fun drawCards(amount: Int) {
        if (amount > 0) {
            hand.addAll(deck.take(amount))
            deck = deck.drop(amount).toMutableList()
        }
    }

    fun playCard(gameCard: GameCard): Int {
        return if (hand.contains(gameCard) && energy >= gameCard.energyCost) {
            hand.remove(gameCard)
            energy -= gameCard.energyCost
            gameCard.attack
        } else {
            0
        }
    }
}