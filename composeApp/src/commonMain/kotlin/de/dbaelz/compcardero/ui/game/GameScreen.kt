package de.dbaelz.compcardero.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import de.dbaelz.compcardero.data.GameConfig
import de.dbaelz.compcardero.data.GameDeck
import de.dbaelz.compcardero.ui.cards.Board
import de.dbaelz.compcardero.ui.endgame.EndGameScreen
import de.dbaelz.compcardero.ui.game.GameScreenContract.Event
import de.dbaelz.compcardero.ui.game.GameScreenContract.State

class GameScreen(
    private val playerName: String,
    private val gameConfig: GameConfig,
    private val gameDeck: GameDeck
) : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { GameScreenModel(playerName, gameConfig, gameDeck) }

        // TODO: Find a way to do this outside of this composable
        val navigationState by screenModel.navigation.collectAsState(null)
        val navigator = LocalNavigator.currentOrThrow
        when (val navState = navigationState) {
            is GameScreenContract.Navigation.EndGame -> {
                navigator.replace(
                    EndGameScreen(
                        navState.endScreenImageRes,
                        navState.winner,
                        navState.loser
                    )
                )
            }

            null -> {}
        }

        val state by screenModel.state.collectAsState()
        when (val currentState = state) {
            State.Loading -> Loading()
            is State.Game -> {
                Board(
                    deckCard = currentState.deckCard,
                    player = currentState.player,
                    opponent = currentState.opponent,
                    isPlayerActive = currentState.isPlayerActive,
                    onCardSelected = { screenModel.sendEvent(Event.CardSelected(it)) },
                    onEndTurnClicked = { screenModel.sendEvent(Event.EndTurnClicked) }
                )
            }
        }
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.onPrimary
        )
    }
}