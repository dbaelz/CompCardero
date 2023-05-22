package de.dbaelz.compcardero.ui.settings

class SettingsScreenContract {
    object State

    sealed interface Event {
        object BackClicked : Event
    }

    sealed interface Navigation {
        object Back : Navigation
    }
}