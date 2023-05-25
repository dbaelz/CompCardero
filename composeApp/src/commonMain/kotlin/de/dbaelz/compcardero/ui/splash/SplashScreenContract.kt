package de.dbaelz.compcardero.ui.splash

interface SplashScreenContract {
    object State

    sealed interface Event {
        object ScreenAutoSwitch : Event
        object ScreenClicked : Event
    }

    sealed interface Navigation {
        object MainMenu : Navigation
    }
}