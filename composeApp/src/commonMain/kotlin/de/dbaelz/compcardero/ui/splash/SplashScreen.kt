package de.dbaelz.compcardero.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import de.dbaelz.compcardero.MR
import de.dbaelz.compcardero.backgroundBrush
import de.dbaelz.compcardero.getPlatformName
import de.dbaelz.compcardero.ui.mainmenu.MainMenuScreen
import de.dbaelz.compcardero.ui.splash.SplashScreenContract.Event
import de.dbaelz.compcardero.ui.splash.SplashScreenContract.Navigation
import de.dbaelz.compcardero.ui.splash.SplashScreenContract.State
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.stringResource

class SplashScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { SplashScreenModel() }

        // TODO: Find a way to do this outside of this composable
        val navigationState by screenModel.navigation.collectAsState(null)
        when (navigationState) {
            Navigation.MainMenu -> {
                Navigator(MainMenuScreen())
                return
            }

            null -> {}
        }

        Content { screenModel.sendEvent(it) }
    }
}

@Composable
private fun Content(sendEvent: (Event) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush)
            .clickable { sendEvent(Event.ScreenClicked) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colors.onPrimary
        ) {
            Text(
                text = "${stringResource(MR.strings.splash_headline)} ${getPlatformName()}",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(MR.strings.splash_subline),
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
