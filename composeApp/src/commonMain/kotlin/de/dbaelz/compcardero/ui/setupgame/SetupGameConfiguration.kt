package de.dbaelz.compcardero.ui.setupgame

data class SetupGameConfiguration(
    var playerName: String,
    var gameDeckNames: List<String>,
    var deckSize: Int,
    var startHandSize: Int,
    var maxCardDrawPerTurn: Int,
    var maxHandSize: Int,
    var startHealth: Int,
    var startEnergy: Int,
    var energyPerTurn: Int,
    var energySlotsPerTurn: Int,
    var maxEnergySlots: Int,
    var gameDeckSelected: String = gameDeckNames.first()
)