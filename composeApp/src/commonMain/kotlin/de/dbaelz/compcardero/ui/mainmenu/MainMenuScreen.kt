package de.dbaelz.compcardero.ui.mainmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import de.dbaelz.compcardero.ui.about.AboutScreen
import de.dbaelz.compcardero.ui.game.GameScreen
import de.dbaelz.compcardero.ui.settings.SettingsScreen
import de.dbaelz.compcardero.ui.setupgame.SetupGameScreen
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

class MainMenuScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { MainMenuScreenModel() }

        // TODO: Find a way to do this outside of this composable
        val navigationState by screenModel.navigation.collectAsState(null)
        val navigator = LocalNavigator.currentOrThrow
        when (navigationState) {
            MainMenuScreenContract.Navigation.NewGame -> navigator.push(SetupGameScreen())
            MainMenuScreenContract.Navigation.Settings -> navigator.push(SettingsScreen())
            MainMenuScreenContract.Navigation.About -> navigator.push(AboutScreen())

            null -> {}
        }

        val state by screenModel.state.collectAsState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            state.menuItems.forEach {
                Row(modifier = Modifier
                    .clickable { screenModel.sendEvent(it.event) }) {
                    if (it.icon != null) {
                        Icon(painter = painterResource(it.icon), contentDescription = null)
                    } else {
                        // TODO: Spacer
                    }

                    Text(
                        text = stringResource(it.text),
                        color = MaterialTheme.colors.onPrimary,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}