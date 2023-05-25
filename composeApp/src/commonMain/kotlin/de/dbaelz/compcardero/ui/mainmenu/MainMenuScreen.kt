package de.dbaelz.compcardero.ui.mainmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import de.dbaelz.compcardero.ui.about.AboutScreen
import de.dbaelz.compcardero.ui.cards.TeaserCards
import de.dbaelz.compcardero.ui.mainmenu.MainMenuScreenContract.Event
import de.dbaelz.compcardero.ui.mainmenu.MainMenuScreenContract.Navigation
import de.dbaelz.compcardero.ui.settings.SettingsScreen
import de.dbaelz.compcardero.ui.setupgame.SetupGameScreen
import dev.icerock.moko.resources.compose.stringResource

class MainMenuScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { MainMenuScreenModel() }

        // TODO: Find a way to do this outside of this composable
        val navigationState by screenModel.navigation.collectAsState(null)
        val navigator = LocalNavigator.currentOrThrow
        when (navigationState) {
            Navigation.NewGame -> navigator.push(SetupGameScreen())
            Navigation.Settings -> navigator.push(SettingsScreen())
            Navigation.About -> navigator.push(AboutScreen())

            null -> {}
        }

        val state by screenModel.state.collectAsState()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary),
            contentAlignment = Alignment.Center
        ) {
            TeaserCards(0.8f, alpha = 0.2f)

            Menu(state.menuItems) { screenModel.sendEvent(it) }
        }
    }
}

@Composable
private fun Menu(menuItems: List<MenuItem>, onMenuItemClick: (Event) -> Unit) {
    Column(
        modifier = Modifier.requiredWidth(IntrinsicSize.Max),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        menuItems.forEach {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onMenuItemClick(it.event) },
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = it.icon,
                    contentDescription = null
                )

                Text(
                    text = stringResource(it.text),
                    modifier = Modifier.weight(1f, false),
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}