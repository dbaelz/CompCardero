package de.dbaelz.compcardero.splash

import cafe.adriel.voyager.core.model.coroutineScope
import de.dbaelz.compcardero.BaseStateScreenModel
import de.dbaelz.compcardero.splash.SplashScreenContract.Event
import de.dbaelz.compcardero.splash.SplashScreenContract.Navigation
import de.dbaelz.compcardero.splash.SplashScreenContract.State
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch


class SplashScreenModel : BaseStateScreenModel<State, Event, Navigation>(State.Content) {
    init {
        coroutineScope.launch {
            events.scan(state.value, ::reduce).collect(::updateState)
        }
    }

    private fun reduce(state: State, event: Event): State {
        return when (event) {
            Event.OnClicked -> {
                navigate(Navigation.MainMenu)
                state
            }
        }
    }
}
