package de.dbaelz.compcardero.ui.mainmenu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Settings
import cafe.adriel.voyager.core.model.coroutineScope
import de.dbaelz.compcardero.MR
import de.dbaelz.compcardero.ui.BaseStateScreenModel
import de.dbaelz.compcardero.ui.mainmenu.MainMenuScreenContract.Event
import de.dbaelz.compcardero.ui.mainmenu.MainMenuScreenContract.Navigation
import de.dbaelz.compcardero.ui.mainmenu.MainMenuScreenContract.State
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch


class MainMenuScreenModel : BaseStateScreenModel<State, Event, Navigation>(State(mainMenuItems)) {
    init {
        coroutineScope.launch {
            events.scan(state.value, ::reduce).collect(::updateState)
        }
    }

    private fun reduce(state: State, event: Event): State {
        return when (event) {
            Event.NewGameClicked -> {
                navigate(Navigation.NewGame)
                state
            }

            Event.SettingsClicked -> {
                navigate(Navigation.Settings)
                state
            }

            Event.AboutClicked -> {
                navigate(Navigation.About)
                state
            }
        }
    }
}

private val mainMenuItems = listOf(
    MenuItem(Icons.Filled.PlayCircleFilled, MR.strings.main_new_game, Event.NewGameClicked),
    MenuItem(Icons.Filled.Settings, MR.strings.main_settings, Event.SettingsClicked),
    MenuItem(Icons.Filled.Info, MR.strings.main_about, Event.AboutClicked)
)
