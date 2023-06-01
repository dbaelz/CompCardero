package de.dbaelz.compcardero.ui.settings.gameconfiguration

import cafe.adriel.voyager.core.model.coroutineScope
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValue
import com.russhwolf.settings.serialization.encodeValue
import de.dbaelz.compcardero.data.SettingsKey
import de.dbaelz.compcardero.ui.BaseStateScreenModel
import de.dbaelz.compcardero.ui.settings.gameconfiguration.SettingsGameConfigurationScreenContract.Event
import de.dbaelz.compcardero.ui.settings.gameconfiguration.SettingsGameConfigurationScreenContract.Navigation
import de.dbaelz.compcardero.ui.settings.gameconfiguration.SettingsGameConfigurationScreenContract.State
import de.dbaelz.compcardero.ui.setupgame.SetupGameConfiguration
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
class SettingsGameConfigurationScreenModel :
    BaseStateScreenModel<State, Event, Navigation>(State.Loading),
    KoinComponent {
    private val settings: Settings by inject()

    init {
        coroutineScope.launch {
            events.scan(state.value, ::reduce).collect(::updateState)
        }

        coroutineScope.launch {
            delay(200)
            val setupGameConfiguration = settings.decodeValue(
                SetupGameConfiguration.serializer(),
                SettingsKey.GAME_CONFIGURATION.name,
                SetupGameConfiguration()
            )
            sendEvent(Event.Loaded(setupGameConfiguration))
        }
    }

    private fun reduce(state: State, event: Event): State {
        return when (event) {
            is Event.Loaded -> {
                State.Content(event.setupGameConfiguration)
            }

            Event.BackClicked -> {
                navigate(Navigation.Back)
                state
            }

            is Event.SaveClicked -> {
                // TODO: Validate input
                settings.encodeValue(
                    SetupGameConfiguration.serializer(),
                    SettingsKey.GAME_CONFIGURATION.name,
                    event.setupGameConfiguration
                )
                navigate(Navigation.Back)
                state
            }
        }
    }
}
