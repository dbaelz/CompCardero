package de.dbaelz.compcardero.ui.about

class AboutScreenContract {
    object State

    sealed interface Event {
        object CloseClicked : Event
    }

    sealed interface Navigation {
        object MainMenu : Navigation
    }
}