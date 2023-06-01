package de.dbaelz.compcardero.ui.settings

import cafe.adriel.voyager.core.model.coroutineScope
import com.russhwolf.settings.Settings
import de.dbaelz.compcardero.ui.BaseStateScreenModel
import de.dbaelz.compcardero.ui.settings.SettingsScreenContract.Event
import de.dbaelz.compcardero.ui.settings.SettingsScreenContract.Navigation
import de.dbaelz.compcardero.ui.settings.SettingsScreenContract.State
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class SettingsScreenModel : BaseStateScreenModel<State, Event, Navigation>(State), KoinComponent {
    private val settings: Settings by inject()

    init {
        coroutineScope.launch {
            events.scan(state.value, ::reduce).collect(::updateState)
        }
    }

    private fun reduce(state: State, event: Event): State {
        return when (event) {
            Event.GameConfigurationClicked -> {
                navigate(Navigation.SettingsGameConfiguration)
                state
            }

            Event.BackClicked -> {
                navigate(Navigation.Back)
                state
            }
        }
    }
}
