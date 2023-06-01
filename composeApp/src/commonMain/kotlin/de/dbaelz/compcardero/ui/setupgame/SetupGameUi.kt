package de.dbaelz.compcardero.ui.setupgame

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import de.dbaelz.compcardero.MR
import de.dbaelz.compcardero.data.game.GameConfig
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SetupGameUi(
    paddingValues: PaddingValues,
    setupGameUiState: SetupGameUiState,
    confirmButton: @Composable () -> Unit
) {
    val configItems = createConfigItems(setupGameUiState)

    SetupGameContent(
        paddingValues = paddingValues,
        playerNameTextField = {
            PlayerNameTextField(setupGameUiState.playerName) {
                setupGameUiState.playerName = it
            }
        },
        gameDecks = {
            GameDecks(
                setupGameUiState.gameDeckNames,
                setupGameUiState.gameDeckSelected
            ) { setupGameUiState.gameDeckSelected = it }
        },
        confirmButton = {
            confirmButton()
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

@Composable
private fun SetupGameContent(
    paddingValues: PaddingValues,
    playerNameTextField: @Composable () -> Unit,
    gameDecks: @Composable () -> Unit,
    confirmButton: @Composable () -> Unit,
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
            confirmButton()
        }
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

@Stable
class SetupGameUiState(gameConfig: GameConfig) {
    var playerName: String by mutableStateOf(gameConfig.playerName)
    var gameDeckNames: List<String> by mutableStateOf(gameConfig.gameDeckNames)
    var deckSize: Int by mutableStateOf(gameConfig.deckSize)
    var startHandSize: Int by mutableStateOf(gameConfig.startHandSize)
    var maxCardDrawPerTurn: Int by mutableStateOf(gameConfig.maxCardDrawPerTurn)
    var maxHandSize: Int by mutableStateOf(gameConfig.maxHandSize)
    var startHealth: Int by mutableStateOf(gameConfig.startHealth)
    var startEnergy: Int by mutableStateOf(gameConfig.startEnergy)
    var energyPerTurn: Int by mutableStateOf(gameConfig.energyPerTurn)
    var energySlotsPerTurn: Int by mutableStateOf(gameConfig.energySlotsPerTurn)
    var maxEnergySlots: Int by mutableStateOf(gameConfig.maxEnergySlots)

    var gameDeckSelected: String by mutableStateOf(gameConfig.gameDeckSelected)

    companion object {
        val Saver = Saver<SetupGameUiState, List<Any>>(
            save = {
                listOf(
                    it.playerName,
                    it.gameDeckNames,
                    it.deckSize,
                    it.startHandSize,
                    it.maxCardDrawPerTurn,
                    it.maxHandSize,
                    it.startHealth,
                    it.startEnergy,
                    it.energyPerTurn,
                    it.energySlotsPerTurn,
                    it.maxEnergySlots
                )
            },
            restore = {
                SetupGameUiState(
                    GameConfig(
                        playerName = it[0] as String,
                        gameDeckNames = it[1] as List<String>,
                        deckSize = it[2] as Int,
                        startHandSize = it[3] as Int,
                        maxCardDrawPerTurn = it[4] as Int,
                        maxHandSize = it[5] as Int,
                        startHealth = it[6] as Int,
                        startEnergy = it[7] as Int,
                        energyPerTurn = it[8] as Int,
                        energySlotsPerTurn = it[9] as Int,
                        maxEnergySlots = it[10] as Int
                    )
                )
            },
        )
    }
}

@Composable
fun rememberSetupGameUiState(
    gameConfig: GameConfig
): SetupGameUiState = rememberSaveable(saver = SetupGameUiState.Saver) {
    SetupGameUiState(gameConfig)
}

private data class ConfigItem(
    val value: Int,
    val labelRes: StringResource,
    val onValueChange: (Int) -> Unit
)

private fun createConfigItems(setupGameUiState: SetupGameUiState): List<ConfigItem> {
    return listOf(
        ConfigItem(
            setupGameUiState.deckSize,
            MR.strings.setupgame_textfield_decksize
        ) { setupGameUiState.deckSize = it },
        ConfigItem(
            setupGameUiState.startHandSize,
            MR.strings.setupgame_textfield_starthandsize
        ) {
            setupGameUiState.startHandSize = it
        },
        ConfigItem(
            setupGameUiState.maxCardDrawPerTurn,
            MR.strings.setupgame_textfield_maxcarddrawperturn
        ) { setupGameUiState.maxCardDrawPerTurn = it },
        ConfigItem(
            setupGameUiState.maxHandSize,
            MR.strings.setupgame_textfield_maxhandsize
        ) {
            setupGameUiState.maxHandSize = it
        },
        ConfigItem(
            setupGameUiState.startHealth,
            MR.strings.setupgame_textfield_starthealth
        ) {
            setupGameUiState.startHealth = it
        },
        ConfigItem(
            setupGameUiState.startEnergy,
            MR.strings.setupgame_textfield_startenergy
        ) {
            setupGameUiState.startEnergy = it
        },
        ConfigItem(
            setupGameUiState.energyPerTurn,
            MR.strings.setupgame_textfield_energyperturn
        ) {
            setupGameUiState.energyPerTurn = it
        },
        ConfigItem(
            setupGameUiState.energySlotsPerTurn,
            MR.strings.setupgame_textfield_energyslotsperturn
        ) {
            setupGameUiState.energySlotsPerTurn = it
        },
        ConfigItem(
            setupGameUiState.maxEnergySlots,
            MR.strings.setupgame_textfield_maxenergyslots
        ) {
            setupGameUiState.maxEnergySlots = it
        },
    )
}
