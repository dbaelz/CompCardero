package de.dbaelz.compcardero.data

import de.dbaelz.compcardero.data.game.GameConfig

interface ValidateGameConfiguration {
    operator fun invoke(gameConfig: GameConfig): ValidationResult
}

class ValidateGameConfigurationImpl : ValidateGameConfiguration {
    override fun invoke(gameConfig: GameConfig): ValidationResult {
        // TODO: Add validation code
        return ValidationResult.Success(
            GameConfig(
                deckSize = gameConfig.deckSize,
                startHandSize = gameConfig.startHandSize,
                maxCardDrawPerTurn = gameConfig.maxCardDrawPerTurn,
                maxHandSize = gameConfig.maxHandSize,
                startHealth = gameConfig.startHealth,
                startEnergy = gameConfig.startEnergy,
                energyPerTurn = gameConfig.energyPerTurn,
                energySlotsPerTurn = gameConfig.energySlotsPerTurn,
                maxEnergySlots = gameConfig.maxEnergySlots
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


