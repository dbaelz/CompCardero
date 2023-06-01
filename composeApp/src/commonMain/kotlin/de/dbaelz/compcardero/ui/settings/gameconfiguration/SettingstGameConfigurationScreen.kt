package de.dbaelz.compcardero.ui.settings.gameconfiguration

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import de.dbaelz.compcardero.ui.settings.gameconfiguration.SettingsGameConfigurationScreenContract.Event
import de.dbaelz.compcardero.ui.settings.gameconfiguration.SettingsGameConfigurationScreenContract.Navigation
import de.dbaelz.compcardero.ui.setupgame.SetupGameUi
import de.dbaelz.compcardero.ui.setupgame.rememberSetupGameUiState
import dev.icerock.moko.resources.compose.stringResource

class SettingsGameConfigurationScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { SettingsGameConfigurationScreenModel() }

        // TODO: Find a way to do this outside of this composable
        val navigationState by screenModel.navigation.collectAsState(null)
        val navigator = LocalNavigator.currentOrThrow
        when (navigationState) {
            Navigation.Back -> navigator.pop()
            null -> {}

        }

        val state by screenModel.state.collectAsState()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(MR.strings.settings_title)) },
                    navigationIcon = {
                        IconButton(onClick = { screenModel.sendEvent(Event.BackClicked) }) {
                            Icon(Icons.Filled.ArrowBack, null)
                        }
                    }
                )
            }
        ) {
            when (val currentState = state) {
                SettingsGameConfigurationScreenContract.State.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is SettingsGameConfigurationScreenContract.State.Content -> {
                    SettingsContent(it, currentState.gameConfig) { gameConfig ->
                        screenModel.sendEvent(Event.SaveClicked(gameConfig))
                    }
                }
            }

        }
    }
}

@Composable
private fun SettingsContent(
    paddingValues: PaddingValues,
    gameConfig: GameConfig,
    onSaveClicked: (GameConfig) -> Unit
) {
    val gameConfigurationState = rememberSetupGameUiState(gameConfig)

    SetupGameUi(paddingValues, gameConfigurationState) {
        SaveGameConfig {
            onSaveClicked(
                GameConfig(
                    playerName = gameConfigurationState.playerName,
                    gameDeckNames = gameConfigurationState.gameDeckNames,
                    deckSize = gameConfigurationState.deckSize,
                    startHandSize = gameConfigurationState.startHandSize,
                    maxCardDrawPerTurn = gameConfigurationState.maxCardDrawPerTurn,
                    maxHandSize = gameConfigurationState.maxHandSize,
                    startHealth = gameConfigurationState.startHealth,
                    startEnergy = gameConfigurationState.startEnergy,
                    energyPerTurn = gameConfigurationState.energyPerTurn,
                    energySlotsPerTurn = gameConfigurationState.energySlotsPerTurn,
                    maxEnergySlots = gameConfigurationState.maxEnergySlots,
                    gameDeckSelected = gameConfigurationState.gameDeckSelected
                )
            )
        }
    }
}

@Composable
private fun SaveGameConfig(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(TextFieldDefaults.MinHeight),
    ) {
        Text(
            text = stringResource(MR.strings.settings_gameconfiguration_save),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}