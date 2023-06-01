package de.dbaelz.compcardero.ui.setupgame

import cafe.adriel.voyager.core.model.coroutineScope
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValue
import de.dbaelz.compcardero.data.SettingsKey
import de.dbaelz.compcardero.data.ValidateGameConfiguration
import de.dbaelz.compcardero.data.ValidationResult
import de.dbaelz.compcardero.data.game.GameConfig
import de.dbaelz.compcardero.data.game.GameDeck
import de.dbaelz.compcardero.ui.BaseStateScreenModel
import de.dbaelz.compcardero.ui.setupgame.SetupGameScreenContract.Event
import de.dbaelz.compcardero.ui.setupgame.SetupGameScreenContract.Navigation
import de.dbaelz.compcardero.ui.setupgame.SetupGameScreenContract.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class SetupGameScreenModel(
    private val gameConfig: GameConfig,
    private val gameDecks: List<GameDeck>
) : BaseStateScreenModel<State, Event, Navigation>(State.Loading),
    KoinComponent {
    private val validateGameConfiguration: ValidateGameConfiguration by inject()

    private val settings: Settings by inject()

    init {
        coroutineScope.launch {
            events.scan(state.value, ::reduce).collect(::updateState)
        }

        coroutineScope.launch {
            delay(200)
            val gameConfig = settings.decodeValue(
                GameConfig.serializer(),
                SettingsKey.GAME_CONFIG.name,
                GameConfig()
            )
            sendEvent(Event.Loaded(gameConfig))
        }
    }

    private fun reduce(state: State, event: Event): State {
        return when (event) {
            is Event.Loaded -> {
                State.Content(event.gameConfig)
            }

            is Event.StartGame -> {
                // TODO: Validate input. Navigate to GameScreen when valid, otherwise show errors
                val validationResult = validateGameConfiguration.invoke(event.gameConfig)

                when (validationResult) {
                    is ValidationResult.Success -> {
                        navigate(Navigation.Game(
                            event.gameConfig.playerName.ifEmpty { "Player" },
                            validationResult.gameConfig,
                            gameDecks.first { it.name == event.gameConfig.gameDeckSelected }
                        ))
                    }

                    is ValidationResult.Failure -> {
                        // TODO: Handle failure
                    }
                }
                state
            }

            Event.BackClicked -> {
                navigate(Navigation.Back)
                state
            }
        }
    }
}
