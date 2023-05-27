package de.dbaelz.compcardero.ui.about

class AboutScreenContract {
    data class State(val platformName: String, val appVersion: String)

    sealed interface Event {
        object BackClicked : Event
    }

    sealed interface Navigation {
        object Back : Navigation
    }
}