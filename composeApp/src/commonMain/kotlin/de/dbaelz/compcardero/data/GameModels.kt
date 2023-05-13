package de.dbaelz.compcardero.data

import dev.icerock.moko.resources.ImageResource

data class GameConfig(
    val startHandSize: Int = 3,
    val maxHandSize: Int = 5,
    val startHealth: Int = 10,
    val startEnergy: Int = 3,
    val energyPerTurn: Int = 1,
    val energySlotsPerTurn: Int = 1,
    val maxEnergySlots: Int = 10
)

data class GameCard(
    val id: Int,
    val attack: Int,
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
