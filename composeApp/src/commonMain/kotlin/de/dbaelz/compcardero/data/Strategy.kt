package de.dbaelz.compcardero.data

abstract class Strategy {
    abstract fun nextCard(cards: List<GameCard>, energy: Int, energySlots: Int): GameCard?

    internal fun playableCards(cards: List<GameCard>, energy: Int): List<GameCard> {
        return cards.filter { it.energyCost <= energy }
    }
}

class RandomStrategy : Strategy() {
    override fun nextCard(cards: List<GameCard>, energy: Int, energySlots: Int): GameCard? {
        return playableCards(cards, energy).randomOrNull()
    }
}

class MostExpensiveFirstStrategy : Strategy() {
    override fun nextCard(cards: List<GameCard>, energy: Int, energySlots: Int): GameCard? {
        return playableCards(cards, energy).maxByOrNull { it.energyCost }
    }
}

class CheapestFirstStrategy : Strategy() {
    override fun nextCard(cards: List<GameCard>, energy: Int, energySlots: Int): GameCard? {
        return playableCards(cards, energy).minByOrNull { it.energyCost }
    }
}