package de.dbaelz.compcardero.ui.settings.gameconfiguration

import cafe.adriel.voyager.core.model.coroutineScope
import com.russhwolf.settings.Settings
import de.dbaelz.compcardero.ui.BaseStateScreenModel
import de.dbaelz.compcardero.ui.settings.gameconfiguration.SettingsGameConfigurationScreenContract.Event
import de.dbaelz.compcardero.ui.settings.gameconfiguration.SettingsGameConfigurationScreenContract.Navigation
import de.dbaelz.compcardero.ui.settings.gameconfiguration.SettingsGameConfigurationScreenContract.State
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class SettingsGameConfigurationScreenModel : BaseStateScreenModel<State, Event, Navigation>(State),
    KoinComponent {
    private val settings: Settings by inject()

    init {
        coroutineScope.launch {
            events.scan(state.value, ::reduce).collect(::updateState)
        }
    }

    private fun reduce(state: State, event: Event): State {
        return when (event) {
            Event.BackClicked -> {
                navigate(Navigation.Back)
                state
            }
        }
    }
}
