package de.dbaelz.compcardero.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen

class GameScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { GameScreenModel() }

        // TODO: Find a way to do this outside of this composable
        val navigationState by screenModel.navigation.collectAsState(null)
        /*when (navigationState) {
            null -> {}
        }*/

        val state by screenModel.state.collectAsState()
        Box(modifier = Modifier.fillMaxSize().background(Color.DarkGray)) {

        }
    }
}