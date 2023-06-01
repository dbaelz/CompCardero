package de.dbaelz.compcardero.ui.setupgame

import cafe.adriel.voyager.core.model.coroutineScope
import de.dbaelz.compcardero.data.ValidateGameConfiguration
import de.dbaelz.compcardero.data.ValidationResult
import de.dbaelz.compcardero.data.game.GameConfig
import de.dbaelz.compcardero.data.game.GameDeck
import de.dbaelz.compcardero.ui.BaseStateScreenModel
import de.dbaelz.compcardero.ui.setupgame.SetupGameScreenContract.Event
import de.dbaelz.compcardero.ui.setupgame.SetupGameScreenContract.Navigation
import de.dbaelz.compcardero.ui.setupgame.SetupGameScreenContract.State
import de.dbaelz.compcardero.ui.setupgame.SetupGameScreenContract.TextContent
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class SetupGameScreenModel(
    private val gameConfig: GameConfig,
    private val gameDecks: List<GameDeck>
) : BaseStateScreenModel<State, Event, Navigation>(createInitialState(gameConfig, gameDecks)),
    KoinComponent {
    private val validateGameConfiguration: ValidateGameConfiguration by inject()

    init {
        coroutineScope.launch {
            events.scan(state.value, ::reduce).collect(::updateState)
        }
    }

    private fun reduce(state: State, event: Event): State {
        return when (event) {
            is Event.StartGame -> {
                // TODO: Validate input. Navigate to GameScreen when valid, otherwise show errors
                val validationResult = validateGameConfiguration.invoke(event.gameConfig)

                when (validationResult) {
                    is ValidationResult.Success -> {
                        navigate(Navigation.Game(
                            event.gameConfig.playerName.ifEmpty { "Player" },
                            validationResult.gameConfig,
                            gameDecks.first { it.name == event.gameConfig.gameDeckSelected }
                        ))
                    }

                    is ValidationResult.Failure -> {
                        // TODO: Handle failure
                    }
                }
                state
            }

            Event.BackClicked -> {
                navigate(Navigation.Back)
                state
            }
        }
    }

    companion object {
        private fun createInitialState(gameConfig: GameConfig, gameDecks: List<GameDeck>): State {
            return State(
                deckSize = TextContent(gameConfig.deckSize),
                startHandSize = TextContent(gameConfig.startHandSize),
                maxCardDrawPerTurn = TextContent(gameConfig.maxCardDrawPerTurn),
                maxHandSize = TextContent(gameConfig.maxHandSize),
                startHealth = TextContent(gameConfig.startHealth),
                startEnergy = TextContent(gameConfig.startEnergy),
                energyPerTurn = TextContent(gameConfig.energyPerTurn),
                energySlotsPerTurn = TextContent(gameConfig.energySlotsPerTurn),
                maxEnergySlots = TextContent(gameConfig.maxEnergySlots),
                gameDeckNames = gameDecks.map { it.name },
            )
        }
    }
}
