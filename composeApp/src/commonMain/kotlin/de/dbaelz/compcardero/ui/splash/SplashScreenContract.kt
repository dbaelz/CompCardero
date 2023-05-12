package de.dbaelz.compcardero.ui.splash

class SplashScreenContract {
    sealed interface State {
        object Loading : State
        data class Content(val infoText: String) : State
    }

    sealed interface Event {
        data class DataLoaded(val infoText: String) : Event
        object ScreenClicked : Event
    }

    sealed interface Navigation {
        object MainMenu : Navigation
    }
}