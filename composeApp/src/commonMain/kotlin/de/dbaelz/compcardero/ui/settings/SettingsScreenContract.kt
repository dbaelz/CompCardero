package de.dbaelz.compcardero.ui.settings

interface SettingsScreenContract {
    object State

    sealed interface Event {
        object GameConfigurationClicked : Event
        object BackClicked : Event
    }

    sealed interface Navigation {
        object GameConfiguration : Navigation
        object Back : Navigation
    }
}