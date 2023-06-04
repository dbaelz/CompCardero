package de.dbaelz.compcardero.ui.settings

interface SettingsScreenContract {
    object State

    sealed interface Event {
        object GameConfigurationClicked : Event

        data class SaveConfigClicked(val darkTheme: Boolean) : Event
        object BackClicked : Event
    }

    sealed interface Navigation {
        object SettingsGameConfiguration : Navigation
        object Back : Navigation
    }
}