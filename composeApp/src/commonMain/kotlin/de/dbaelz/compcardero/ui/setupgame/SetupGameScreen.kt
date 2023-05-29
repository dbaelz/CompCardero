package de.dbaelz.compcardero.ui.setupgame

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
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
import dev.icerock.moko.resources.StringResource
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

        // TODO: Use a state holder and extract it
        var playerName by rememberSaveable { mutableStateOf("") }
        var deckSize by rememberSaveable { mutableStateOf(state.deckSize.value) }
        var startHandSize by rememberSaveable { mutableStateOf(state.startHandSize.value) }
        var maxCardDrawPerTurn by rememberSaveable { mutableStateOf(state.maxCardDrawPerTurn.value) }
        var maxHandSize by rememberSaveable { mutableStateOf(state.maxHandSize.value) }
        var startHealth by rememberSaveable { mutableStateOf(state.startHealth.value) }
        var startEnergy by rememberSaveable { mutableStateOf(state.startEnergy.value) }
        var energyPerTurn by rememberSaveable { mutableStateOf(state.energyPerTurn.value) }
        var energySlotsPerTurn by rememberSaveable { mutableStateOf(state.energySlotsPerTurn.value) }
        var maxEnergySlots by rememberSaveable { mutableStateOf(state.maxEnergySlots.value) }
        var gameDeckName by rememberSaveable { mutableStateOf(state.gameDeckNames.first()) }

        val configItems = listOf(
            ConfigItem(deckSize, MR.strings.setupgame_textfield_decksize) { deckSize = it },
            ConfigItem(startHandSize, MR.strings.setupgame_textfield_starthandsize) {
                startHandSize = it
            },
            ConfigItem(
                maxCardDrawPerTurn,
                MR.strings.setupgame_textfield_maxcarddrawperturn
            ) { maxCardDrawPerTurn = it },
            ConfigItem(maxHandSize, MR.strings.setupgame_textfield_maxhandsize) {
                maxHandSize = it
            },
            ConfigItem(startHealth, MR.strings.setupgame_textfield_starthealth) {
                startHealth = it
            },
            ConfigItem(startEnergy, MR.strings.setupgame_textfield_startenergy) {
                startEnergy = it
            },
            ConfigItem(energyPerTurn, MR.strings.setupgame_textfield_energyperturn) {
                energyPerTurn = it
            },
            ConfigItem(energySlotsPerTurn, MR.strings.setupgame_textfield_energyslotsperturn) {
                energySlotsPerTurn = it
            },
            ConfigItem(maxEnergySlots, MR.strings.setupgame_textfield_maxenergyslots) {
                maxEnergySlots = it
            },
        )

        val onStartGameClick = {
            screenModel.sendEvent(
                Event.StartGame(
                    playerName = playerName,
                    deckSize = deckSize,
                    startHandSize = startHandSize,
                    maxCardDrawPerTurn = maxCardDrawPerTurn,
                    maxHandSize = maxHandSize,
                    startHealth = startHealth,
                    startEnergy = startEnergy,
                    energyPerTurn = energyPerTurn,
                    energySlotsPerTurn = energySlotsPerTurn,
                    maxEnergySlots = maxEnergySlots,
                    gameDeckName = gameDeckName
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
                            onClick = { onStartGameClick() }) {
                            Icon(Icons.Filled.PlayCircleFilled, null)
                        }
                    }
                )
            }
        ) {
            SetupGameContent(
                paddingValues = it,
                playerNameTextField = {
                    PlayerNameTextField(playerName) { playerName = it }
                },
                gameDecks = {
                    GameDecks(state.gameDeckNames, gameDeckName) { gameDeckName = it }
                },
                startButton = {
                    StartGameButton { onStartGameClick() }
                },
                configItems = {
                    items(configItems) { configItem ->
                        NumberTextField(
                            configItem.value,
                            configItem.labelRes,
                            configItem.onValueChange
                        )
                    }
                },
            )
        }
    }
}

@Composable
private fun SetupGameContent(
    paddingValues: PaddingValues,
    playerNameTextField: @Composable () -> Unit,
    gameDecks: @Composable () -> Unit,
    startButton: @Composable () -> Unit,
    configItems: LazyGridScope.() -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(8.dp),
        contentPadding = paddingValues,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            playerNameTextField()
        }

        configItems()

        item(span = { GridItemSpan(2) }) {
            gameDecks()
        }

        item(span = { GridItemSpan(2) }) {
            startButton()
        }
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

@Composable
private fun PlayerNameTextField(playerName: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = playerName,
        onValueChange = onValueChange,
        label = { Text(stringResource(MR.strings.setupgame_textfield_playername)) }
    )
}

@Composable
private fun NumberTextField(value: Int, labelRes: StringResource, onValueChange: (Int) -> Unit) {
    OutlinedTextField(
        value = value.toString(),
        onValueChange = {
            it.toIntOrNull()?.let { value ->
                onValueChange(value)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { Text(stringResource(labelRes)) }
    )
}

@Composable
private fun GameDecks(
    gameDeckNames: List<String>,
    selectedName: String,
    onClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(MR.strings.setupgame_decks_header),
            fontWeight = FontWeight.Bold
        )

        gameDeckNames.forEach {
            Text(
                text = it,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .clip(MaterialTheme.shapes.small)
                    .then(
                        if (it == selectedName) Modifier.background(MaterialTheme.colors.primaryVariant) else Modifier
                    )
                    .clickable { onClick(it) }
                    .padding(8.dp)
            )
        }
    }
}

private data class ConfigItem(
    val value: Int,
    val labelRes: StringResource,
    val onValueChange: (Int) -> Unit
)
