package de.dbaelz.compcardero.ui.game

import cafe.adriel.voyager.core.model.coroutineScope
import de.dbaelz.compcardero.data.Game
import de.dbaelz.compcardero.data.createNewGame
import de.dbaelz.compcardero.ui.BaseStateScreenModel
import de.dbaelz.compcardero.ui.game.GameScreenContract.Event
import de.dbaelz.compcardero.ui.game.GameScreenContract.Navigation
import de.dbaelz.compcardero.ui.game.GameScreenContract.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch


class GameScreenModel : BaseStateScreenModel<State, Event, Navigation>(State.Loading) {
    // TODO: Do it differently, e.g. with DI and inject a game instance
    private lateinit var game: Game

    init {
        coroutineScope.launch {
            events.scan(state.value, ::reduce).collect(::updateState)
        }

        coroutineScope.launch {
            sendEvent(Event.GameInitialized(createNewGame()))
        }
    }

    private fun reduce(state: State, event: Event): State {
        return when (event) {
            is Event.GameInitialized -> {
                check(state is State.Loading)
                game = event.game
                createStateFromPlayerStats()
            }

            is Event.CardSelected -> {
                game.cardSelected(event.gameCard)
                createStateFromPlayerStats()
            }

            Event.EndTurnClicked -> {
                game.endTurnSelected()

                coroutineScope.launch {
                    when (val result = game.determineWinner()) {
                        null -> sendEvent(Event.TurnEnded)
                        else -> navigate(Navigation.EndGame(result.first, result.second))
                    }
                }
                createStateFromPlayerStats()
            }

            Event.TurnEnded -> {
                game.startOpponentTurn()

                coroutineScope.launch {
                    delay(1000)
                    game.playOpponentCards()
                    sendEvent(Event.OpponentTurnEnded)
                }
                createStateFromPlayerStats()
            }

            Event.OpponentTurnEnded -> {
                when (val result = game.determineWinner()) {
                    null -> game.startTurn()
                    else -> navigate(Navigation.EndGame(result.first, result.second))
                }
                createStateFromPlayerStats()
            }
        }
    }

    private fun createStateFromPlayerStats(): State {
        val playerStats = game.getPlayerStats()
        return State.Game(playerStats.first, playerStats.second, game.isPlayerActive)
    }
}