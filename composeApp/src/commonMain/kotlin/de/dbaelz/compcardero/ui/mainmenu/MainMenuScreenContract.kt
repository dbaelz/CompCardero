package de.dbaelz.compcardero.ui.mainmenu

import dev.icerock.moko.resources.ImageResource
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
    val icon: ImageResource?,
    val text: StringResource,
    val event: MainMenuScreenContract.Event
)