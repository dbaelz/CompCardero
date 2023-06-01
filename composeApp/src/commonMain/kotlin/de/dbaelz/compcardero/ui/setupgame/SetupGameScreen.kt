package de.dbaelz.compcardero.ui.setupgame

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import de.dbaelz.compcardero.MR
import de.dbaelz.compcardero.data.game.GameConfig
import de.dbaelz.compcardero.decks.fantasyGameDeck
import de.dbaelz.compcardero.ui.game.GameScreen
import de.dbaelz.compcardero.ui.setupgame.SetupGameScreenContract.Event
import de.dbaelz.compcardero.ui.setupgame.SetupGameScreenContract.Navigation
import de.dbaelz.compcardero.ui.setupgame.SetupGameScreenContract.State
import dev.icerock.moko.resources.compose.stringResource

class SetupGameScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel {
            SetupGameScreenModel(GameConfig(), listOf(fantasyGameDeck))
        }

        // TODO: Find a way to do this outside of this composable
        val navigationState by screenModel.navigation.collectAsState(null)
        val navigator = LocalNavigator.currentOrThrow
        when (val navState = navigationState) {
            is Navigation.Game -> {
                navigator.replace(
                    GameScreen(
                        navState.playerName,
                        navState.gameConfig,
                        navState.gameDeck
                    )
                )
            }

            Navigation.Back -> navigator.pop()
            null -> {}
        }

        val state by screenModel.state.collectAsState()
        when (val currentState = state) {
            State.Loading -> {
                LoadingState(onBackPressed = {
                    screenModel.sendEvent(Event.BackClicked)
                })
            }

            is State.Content -> {
                ContentState(currentState.gameConfig, screenModel)
            }
        }

    }
}

@Composable
private fun LoadingState(onBackPressed: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(MR.strings.setupgame_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun ContentState(gameConfig: GameConfig, screenModel: SetupGameScreenModel) {
    val gameConfigurationState = rememberSetupGameUiState(gameConfig)

    val onStartGameClick: (SetupGameUiState) -> Unit = {
        screenModel.sendEvent(
            Event.StartGame(
                GameConfig(
                    playerName = it.playerName,
                    gameDeckNames = it.gameDeckNames,
                    deckSize = it.deckSize,
                    startHandSize = it.startHandSize,
                    maxCardDrawPerTurn = it.maxCardDrawPerTurn,
                    maxHandSize = it.maxHandSize,
                    startHealth = it.startHealth,
                    startEnergy = it.startEnergy,
                    energyPerTurn = it.energyPerTurn,
                    energySlotsPerTurn = it.energySlotsPerTurn,
                    maxEnergySlots = it.maxEnergySlots,
                    gameDeckSelected = it.gameDeckSelected
                )
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(MR.strings.setupgame_title)) },
                navigationIcon = {
                    IconButton(onClick = { screenModel.sendEvent(Event.BackClicked) }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    IconButton(
                        onClick = { onStartGameClick(gameConfigurationState) }) {
                        Icon(Icons.Filled.PlayCircleFilled, null)
                    }
                }
            )
        }
    ) { paddingValues ->
        SetupGameUi(
            paddingValues,
            gameConfigurationState
        ) { StartGameButton { onStartGameClick(gameConfigurationState) } }
    }
}

@Composable
private fun StartGameButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(TextFieldDefaults.MinHeight),
    ) {
        Text(
            text = stringResource(MR.strings.setupgame_start_game),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}