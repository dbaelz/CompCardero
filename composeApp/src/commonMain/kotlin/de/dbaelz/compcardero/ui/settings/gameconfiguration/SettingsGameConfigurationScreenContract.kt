package de.dbaelz.compcardero.ui.settings.gameconfiguration

import de.dbaelz.compcardero.ui.setupgame.SetupGameConfiguration

interface SettingsGameConfigurationScreenContract {
    sealed interface State {
        object Loading : State
        data class Content(val gameConfiguration: SetupGameConfiguration) : State
    }

    sealed interface Event {
        data class Loaded(val setupGameConfiguration: SetupGameConfiguration) : Event
        object BackClicked : Event
        data class SaveClicked(val setupGameConfiguration: SetupGameConfiguration) : Event
    }

    sealed interface Navigation {
        object Back : Navigation
    }
}