package de.dbaelz.compcardero.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import de.dbaelz.compcardero.MR
import de.dbaelz.compcardero.getPlatformName
import de.dbaelz.compcardero.mainmenu.MainMenuScreen
import de.dbaelz.compcardero.splash.SplashScreenContract.Event
import de.dbaelz.compcardero.splash.SplashScreenContract.Navigation
import de.dbaelz.compcardero.splash.SplashScreenContract.State
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

        val state by screenModel.state.collectAsState()
        when (val currentState = state) {
            State.Loading -> Loading()
            is State.Content -> Content(currentState.infoText) { screenModel.sendEvent(it) }
        }
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = colorResource(MR.colors.onPrimary)
        )
    }
}

@Composable
private fun Content(infoText: String, sendEvent: (Event) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush)
            .clickable { sendEvent(Event.ScreenClicked) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides colorResource(MR.colors.onPrimary)
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

            if (infoText.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .border(
                            width = 2.dp,
                            color = colorResource(MR.colors.onPrimary),
                            shape = RoundedCornerShape(4.dp)
                        ).padding(8.dp),
                    text = infoText,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Left
                )
            }
        }
    }
}

private val backgroundBrush: Brush
    @Composable
    get() = Brush.verticalGradient(
        listOf(
            colorResource(MR.colors.primary),
            colorResource(MR.colors.secondary)
        )
    )