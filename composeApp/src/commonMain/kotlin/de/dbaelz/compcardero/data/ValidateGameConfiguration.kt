package de.dbaelz.compcardero.data

import de.dbaelz.compcardero.ui.setupgame.SetupGameConfiguration
import de.dbaelz.compcardero.data.game.GameConfig

interface ValidateGameConfiguration {
    operator fun invoke(gameConfiguration: SetupGameConfiguration): ValidationResult
}

class ValidateGameConfigurationImpl : ValidateGameConfiguration {
    override fun invoke(gameConfiguration: SetupGameConfiguration): ValidationResult {
        // TODO: Add validation code
        return ValidationResult.Success(
            GameConfig(
                deckSize = gameConfiguration.deckSize,
                startHandSize = gameConfiguration.startHandSize,
                maxCardDrawPerTurn = gameConfiguration.maxCardDrawPerTurn,
                maxHandSize = gameConfiguration.maxHandSize,
                startHealth = gameConfiguration.startHealth,
                startEnergy = gameConfiguration.startEnergy,
                energyPerTurn = gameConfiguration.energyPerTurn,
                energySlotsPerTurn = gameConfiguration.energySlotsPerTurn,
                maxEnergySlots = gameConfiguration.maxEnergySlots
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


