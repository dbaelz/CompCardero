package de.dbaelz.compcardero.ui.splash

import cafe.adriel.voyager.core.model.coroutineScope
import de.dbaelz.compcardero.ui.BaseStateScreenModel
import de.dbaelz.compcardero.ui.splash.SplashScreenContract.Event
import de.dbaelz.compcardero.ui.splash.SplashScreenContract.Navigation
import de.dbaelz.compcardero.ui.splash.SplashScreenContract.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch


class SplashScreenModel : BaseStateScreenModel<State, Event, Navigation>(State) {
    init {
        coroutineScope.launch {
            events.scan(state.value, ::reduce).collect(::updateState)
        }

        coroutineScope.launch {
            delay(5000)
            sendEvent(Event.ScreenAutoSwitch)
        }
    }

    private fun reduce(state: State, event: Event): State {
        return when (event) {
            Event.ScreenAutoSwitch, Event.ScreenClicked -> {
                navigate(Navigation.MainMenu)
                state
            }
        }
    }
}
