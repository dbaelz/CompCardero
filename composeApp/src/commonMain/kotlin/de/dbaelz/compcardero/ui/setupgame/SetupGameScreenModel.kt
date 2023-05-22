package de.dbaelz.compcardero.ui.setupgame

import cafe.adriel.voyager.core.model.coroutineScope
import de.dbaelz.compcardero.data.GameConfig
import de.dbaelz.compcardero.data.GameDeck
import de.dbaelz.compcardero.ui.BaseStateScreenModel
import de.dbaelz.compcardero.ui.setupgame.SetupGameScreenContract.Event
import de.dbaelz.compcardero.ui.setupgame.SetupGameScreenContract.Navigation
import de.dbaelz.compcardero.ui.setupgame.SetupGameScreenContract.State
import de.dbaelz.compcardero.ui.setupgame.SetupGameScreenContract.TextContent
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch


class SetupGameScreenModel(
    private val gameConfig: GameConfig,
    private val gameDecks: List<GameDeck>
) : BaseStateScreenModel<State, Event, Navigation>(createInitialState(gameConfig, gameDecks)) {
    init {
        coroutineScope.launch {
            events.scan(state.value, ::reduce).collect(::updateState)
        }
    }

    private fun reduce(state: State, event: Event): State {
        return when (event) {
            is Event.StartGame -> {
                // TODO: Validate input. Navigate to GameScreen when valid, otherwise show errors
                navigate(Navigation.Game(
                    event.playerName,
                    GameConfig(
                        deckSize = event.deckSize,
                        startHandSize = event.startHandSize,
                        maxCardDrawPerTurn = event.maxCardDrawPerTurn,
                        maxHandSize = event.maxHandSize,
                        startHealth = event.startHealth,
                        startEnergy = event.startEnergy,
                        energyPerTurn = event.energyPerTurn,
                        energySlotsPerTurn = event.energySlotsPerTurn,
                        maxEnergySlots = event.maxEnergySlots
                    ),
                    gameDecks.first { it.name == event.gameDeckName }
                ))
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