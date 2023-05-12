package de.dbaelz.compcardero.ui.splash

import cafe.adriel.voyager.core.model.coroutineScope
import de.dbaelz.compcardero.BaseStateScreenModel
import de.dbaelz.compcardero.ui.splash.SplashScreenContract.Event
import de.dbaelz.compcardero.ui.splash.SplashScreenContract.Navigation
import de.dbaelz.compcardero.ui.splash.SplashScreenContract.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch


class SplashScreenModel : BaseStateScreenModel<State, Event, Navigation>(State.Loading) {
    init {
        coroutineScope.launch {
            events.scan(state.value, ::reduce).collect(::updateState)
        }

        coroutineScope.launch {
            // TODO: Show infos from backend or based on settings
            delay(2000)
            sendEvent(Event.DataLoaded("Hello, this is a very important message from the CompCardero team."))
        }
    }

    private fun reduce(state: State, event: Event): State {
        return when (event) {
            is Event.DataLoaded -> {
                State.Content(event.infoText)
            }

            Event.ScreenClicked -> {
                navigate(Navigation.MainMenu)
                state
            }
        }
    }
}
