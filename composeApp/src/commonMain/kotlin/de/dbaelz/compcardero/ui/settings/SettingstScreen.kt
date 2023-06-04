package de.dbaelz.compcardero.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import de.dbaelz.compcardero.MR
import de.dbaelz.compcardero.ui.settings.SettingsScreenContract.Event
import de.dbaelz.compcardero.ui.settings.gameconfiguration.SettingsGameConfigurationScreen
import dev.icerock.moko.resources.compose.stringResource

class SettingsScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { SettingsScreenModel() }

        // TODO: Find a way to do this outside of this composable
        val navigationState by screenModel.navigation.collectAsState(null)
        val navigator = LocalNavigator.currentOrThrow
        when (navigationState) {
            SettingsScreenContract.Navigation.SettingsGameConfiguration -> navigator.push(
                SettingsGameConfigurationScreen()
            )

            SettingsScreenContract.Navigation.Back -> navigator.pop()
            null -> {}
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(MR.strings.settings_title)) },
                    navigationIcon = {
                        IconButton(onClick = { screenModel.sendEvent(Event.BackClicked) }) {
                            Icon(Icons.Filled.ArrowBack, null)
                        }
                    }
                )
            }
        ) {
            SettingsContent(it, screenModel)
        }
    }
}

@Composable
private fun SettingsContent(
    paddingValues: PaddingValues,
    screenModel: SettingsScreenModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colors.onPrimary
        ) {
            var darkTheme by remember { mutableStateOf(false) }

            Row(
                modifier = Modifier.toggleable(
                    role = Role.Switch,
                    value = darkTheme,
                    onValueChange = { darkTheme = it },
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(MR.strings.settings_dark_theme))

                Spacer(Modifier.width(16.dp))

                Switch(
                    checked = darkTheme,
                    onCheckedChange = null,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colors.primary,
                        uncheckedThumbColor = MaterialTheme.colors.primaryVariant
                    )
                )
            }

            Button(onClick = { screenModel.sendEvent(Event.SaveConfigClicked(darkTheme)) }) {
                Text(text = stringResource(MR.strings.settings_save))
            }


            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { screenModel.sendEvent(Event.GameConfigurationClicked) }) {
                Text(text = stringResource(MR.strings.settings_game_configuration))
            }
        }
    }
}
