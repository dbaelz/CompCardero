package de.dbaelz.compcardero.ui.settings.gameconfiguration

interface SettingsGameConfigurationScreenContract {
    object State

    sealed interface Event {
        object BackClicked : Event
    }

    sealed interface Navigation {
        object Back : Navigation
    }
}