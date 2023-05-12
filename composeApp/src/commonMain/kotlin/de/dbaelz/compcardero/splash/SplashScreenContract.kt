package de.dbaelz.compcardero.splash

class SplashScreenContract {
    sealed interface State {
        // TODO: Add functionality to show an info box based on API call
        object Content : State
    }

    sealed interface Event {
        object OnClicked : Event
    }

    sealed interface Navigation {
        object MainMenu : Navigation
    }
}