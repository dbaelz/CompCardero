package de.dbaelz.compcardero.ui.settings.gameconfiguration

import de.dbaelz.compcardero.data.game.GameConfig

interface SettingsGameConfigurationScreenContract {
    sealed interface State {
        object Loading : State
        data class Content(val gameConfig: GameConfig) : State
    }

    sealed interface Event {
        data class Loaded(val gameConfig: GameConfig) : Event
        object BackClicked : Event
        data class SaveClicked(val gameConfig: GameConfig) : Event
    }

    sealed interface Navigation {
        object Back : Navigation
    }
}