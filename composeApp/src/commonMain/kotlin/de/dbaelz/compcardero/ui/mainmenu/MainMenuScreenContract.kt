package de.dbaelz.compcardero.ui.mainmenu

import androidx.compose.ui.graphics.vector.ImageVector
import dev.icerock.moko.resources.StringResource

interface MainMenuScreenContract {
    data class State(val menuItems: List<MenuItem>)
    sealed interface Event {
        object NewGameClicked : Event
        object SettingsClicked : Event
        object AboutClicked : Event
    }

    sealed interface Navigation {
        object NewGame : Navigation
        object Settings : Navigation
        object About : Navigation
    }
}

data class MenuItem(
    val icon: ImageVector,
    val text: StringResource,
    val event: MainMenuScreenContract.Event
)