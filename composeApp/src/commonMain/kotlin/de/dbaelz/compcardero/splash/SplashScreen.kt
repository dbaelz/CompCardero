package de.dbaelz.compcardero.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.stringResource

class SplashScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { SplashScreenModel() }

        // TODO: Find a way to do this outside of this composable
        val navigationState by screenModel.navigation.collectAsState(null)
        when (navigationState) {
            SplashScreenContract.Navigation.MainMenu -> {
                Navigator(MainMenuScreen())
                return
            }

            null -> {}
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            colorResource(MR.colors.primary),
                            colorResource(MR.colors.secondary)
                        )
                    )
                )
                .clickable { screenModel.sendEvent(Event.OnClicked) },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
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