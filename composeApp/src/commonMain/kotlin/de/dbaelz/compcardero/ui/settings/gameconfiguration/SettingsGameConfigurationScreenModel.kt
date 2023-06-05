package de.dbaelz.compcardero.ui.settings.gameconfiguration

import cafe.adriel.voyager.core.model.coroutineScope
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.encodeValue
import de.dbaelz.compcardero.data.GetGameConfig
import de.dbaelz.compcardero.data.SetGameConfig
import de.dbaelz.compcardero.data.SettingsKey
import de.dbaelz.compcardero.data.game.GameConfig
import de.dbaelz.compcardero.ui.BaseStateScreenModel
import de.dbaelz.compcardero.ui.settings.gameconfiguration.SettingsGameConfigurationScreenContract.Event
import de.dbaelz.compcardero.ui.settings.gameconfiguration.SettingsGameConfigurationScreenContract.Navigation
import de.dbaelz.compcardero.ui.settings.gameconfiguration.SettingsGameConfigurationScreenContract.State
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
class SettingsGameConfigurationScreenModel :
    BaseStateScreenModel<State, Event, Navigation>(State.Loading),
    KoinComponent {
    private val getGameConfig: GetGameConfig by inject()

    private val setGameConfig: SetGameConfig by inject()

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

            Event.BackClicked -> {
                navigate(Navigation.Back)
                state
            }

            is Event.SaveClicked -> {
                // TODO: Validate input
                setGameConfig(event.gameConfig)
                navigate(Navigation.Back)
                state
            }
        }
    }
}
