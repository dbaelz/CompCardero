package de.dbaelz.compcardero.ui.game

import cafe.adriel.voyager.core.model.coroutineScope
import de.dbaelz.compcardero.data.game.Game
import de.dbaelz.compcardero.data.game.GameConfig
import de.dbaelz.compcardero.data.game.GameDeck
import de.dbaelz.compcardero.ui.BaseStateScreenModel
import de.dbaelz.compcardero.ui.game.GameScreenContract.Event
import de.dbaelz.compcardero.ui.game.GameScreenContract.Navigation
import de.dbaelz.compcardero.ui.game.GameScreenContract.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch


class GameScreenModel(
    playerName: String,
    gameConfig: GameConfig,
    gameDeck: GameDeck
) : BaseStateScreenModel<State, Event, Navigation>(State.Loading) {
    private var game: Game
    private val deckCardRes = gameDeck.deckCard
    private val endScreenImageRes = gameDeck.endScreenImageRes

    init {
        coroutineScope.launch {
            events.scan(state.value, ::reduce).collect(::updateState)
        }

        game = Game.create(
            playerName = playerName,
            opponentName = "Bot",
            gameConfig = gameConfig,
            gameDeck = gameDeck
        )

        // TODO: Unnecessary right now. Remove loading state or change for local multiplayer
        coroutineScope.launch {
            sendEvent(
                Event.GameInitialized(game)
            )
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
                        else -> navigate(
                            Navigation.EndGame(
                                endScreenImageRes,
                                result.first,
                                result.second
                            )
                        )
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
                    else -> navigate(
                        Navigation.EndGame(
                            endScreenImageRes,
                            result.first,
                            result.second
                        )
                    )
                }
                createStateFromPlayerStats()
            }
        }
    }

    private fun createStateFromPlayerStats(): State {
        val playerStats = game.getPlayerStats()
        return State.Game(deckCardRes, playerStats.first, playerStats.second, game.isPlayerActive)
    }
}