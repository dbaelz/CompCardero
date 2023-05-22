package de.dbaelz.compcardero.ui.about

class AboutScreenContract {
    object State

    sealed interface Event {
        object BackClicked : Event
    }

    sealed interface Navigation {
        object Back : Navigation
    }
}