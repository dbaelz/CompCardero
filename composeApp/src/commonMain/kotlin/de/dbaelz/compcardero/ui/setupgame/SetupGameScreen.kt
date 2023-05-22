package de.dbaelz.compcardero.ui.setupgame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import de.dbaelz.compcardero.MR
import de.dbaelz.compcardero.data.GameConfig
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
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(MR.strings.setupgame_title)) },
                    navigationIcon = {
                        IconButton(onClick = { screenModel.sendEvent(Event.BackClicked) }) {
                            Icon(Icons.Filled.ArrowBack, null)
                        }
                    }
                )
            }
        ) {
            SetupGameContent(it, state, screenModel)
        }
    }
}

@Composable
private fun SetupGameContent(
    paddingValues: PaddingValues,
    state: State,
    screenModel: SetupGameScreenModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colors.secondary)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        // TODO: Add UI elements to set specific values
        Button(
            onClick = {
                screenModel.sendEvent(
                    Event.StartGame(
                        playerName = "You",
                        deckSize = state.deckSize.value,
                        startHandSize = state.startHandSize.value,
                        maxCardDrawPerTurn = state.maxCardDrawPerTurn.value,
                        maxHandSize = state.maxHandSize.value,
                        startHealth = state.startHealth.value,
                        startEnergy = state.startEnergy.value,
                        energyPerTurn = state.energyPerTurn.value,
                        energySlotsPerTurn = state.energySlotsPerTurn.value,
                        maxEnergySlots = state.maxEnergySlots.value,
                        gameDeckName = state.gameDeckNames.first()
                    )
                )
            }
        ) {
            Text(text = stringResource(MR.strings.setupgame_start_game))
        }
    }
}