package de.dbaelz.compcardero.data

import de.dbaelz.compcardero.data.game.GameConfig

interface ValidateGameConfiguration {
    operator fun invoke(
        deckSize: Int,
        startHandSize: Int,
        maxCardDrawPerTurn: Int,
        maxHandSize: Int,
        startHealth: Int,
        startEnergy: Int,
        energyPerTurn: Int,
        energySlotsPerTurn: Int,
        maxEnergySlots: Int
    ): ValidationResult
}

class ValidateGameConfigurationImpl : ValidateGameConfiguration {
    override fun invoke(
        deckSize: Int,
        startHandSize: Int,
        maxCardDrawPerTurn: Int,
        maxHandSize: Int,
        startHealth: Int,
        startEnergy: Int,
        energyPerTurn: Int,
        energySlotsPerTurn: Int,
        maxEnergySlots: Int
    ): ValidationResult {
        // TODO: Add validation code
        return ValidationResult.Success(
            GameConfig(
                deckSize = deckSize,
                startHandSize = startHandSize,
                maxCardDrawPerTurn = maxCardDrawPerTurn,
                maxHandSize = maxHandSize,
                startHealth = startHealth,
                startEnergy = startEnergy,
                energyPerTurn = energyPerTurn,
                energySlotsPerTurn = energySlotsPerTurn,
                maxEnergySlots = maxEnergySlots
            )
        )
    }

}

sealed interface ValidationResult {
    data class Success(val gameConfig: GameConfig) : ValidationResult
    data class Failure(val error: ValidationError) : ValidationResult {
        enum class ValidationError {

        }
    }
}


