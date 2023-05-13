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
import cafe.adriel.voyager.navigator.Navigator
import de.dbaelz.compcardero.backgroundBrush
import de.dbaelz.compcardero.ui.endgame.EndGameScreen
import de.dbaelz.compcardero.ui.game.GameScreenContract.Event
import de.dbaelz.compcardero.ui.game.GameScreenContract.State
import de.dbaelz.compcardero.ui.mainmenu.MainMenuScreen

class GameScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { GameScreenModel() }

        // TODO: Find a way to do this outside of this composable
        val navigationState by screenModel.navigation.collectAsState(null)
        when (val navState = navigationState) {
            is GameScreenContract.Navigation.EndGame -> {
                Navigator(EndGameScreen(navState.winner, navState.loser))
                return
            }
            null -> {}
        }

        val state by screenModel.state.collectAsState()
        when (val currentState = state) {
            State.Loading -> Loading()
            is State.Game -> {
                Board(
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
            .background(backgroundBrush),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.onPrimary
        )
    }
}