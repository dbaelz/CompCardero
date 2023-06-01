package de.dbaelz.compcardero.ui.setupgame

import cafe.adriel.voyager.core.model.coroutineScope
import de.dbaelz.compcardero.data.GetGameConfig
import de.dbaelz.compcardero.data.GetGameDecks
import de.dbaelz.compcardero.data.ValidateGameConfiguration
import de.dbaelz.compcardero.data.ValidationResult
import de.dbaelz.compcardero.ui.BaseStateScreenModel
import de.dbaelz.compcardero.ui.setupgame.SetupGameScreenContract.Event
import de.dbaelz.compcardero.ui.setupgame.SetupGameScreenContract.Navigation
import de.dbaelz.compcardero.ui.setupgame.SetupGameScreenContract.State
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class SetupGameScreenModel : BaseStateScreenModel<State, Event, Navigation>(State.Loading),
    KoinComponent {
    private val getGameConfig: GetGameConfig by inject()

    private val getGameDecks: GetGameDecks by inject()

    private val validateGameConfiguration: ValidateGameConfiguration by inject()

    init {
        coroutineScope.launch {
            events.scan(state.value, ::reduce).collect(::updateState)
        }

        coroutineScope.launch {
            sendEvent(Event.Loaded(getGameConfig()))
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
                        val gameDecks = getGameDecks()
                        navigate(Navigation.Game(
                            event.gameConfig.playerName.ifEmpty { "Player" },
                            validationResult.gameConfig,
                            gameDecks.firstOrNull { it.name == event.gameConfig.gameDeckSelected }
                                ?: gameDecks.first()
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
