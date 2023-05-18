package de.dbaelz.compcardero.ui.settings

class SettingsScreenContract {
    object State

    sealed interface Event {
        object CloseClicked : Event
    }

    sealed interface Navigation {
        object MainMenu : Navigation
    }
}