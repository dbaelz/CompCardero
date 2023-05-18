package de.dbaelz.compcardero.ui.about

import cafe.adriel.voyager.core.model.coroutineScope
import de.dbaelz.compcardero.ui.BaseStateScreenModel
import de.dbaelz.compcardero.ui.about.AboutScreenContract.Event
import de.dbaelz.compcardero.ui.about.AboutScreenContract.Navigation
import de.dbaelz.compcardero.ui.about.AboutScreenContract.State
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch


class AboutScreenModel : BaseStateScreenModel<State, Event, Navigation>(State) {
    init {
        coroutineScope.launch {
            events.scan(state.value, ::reduce).collect(::updateState)
        }
    }

    private fun reduce(state: State, event: Event): State {
        return when (event) {
            Event.CloseClicked -> {
                navigate(Navigation.MainMenu)
                state
            }
        }
    }
}
