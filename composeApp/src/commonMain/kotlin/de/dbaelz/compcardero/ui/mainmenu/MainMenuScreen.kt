package de.dbaelz.compcardero.ui.mainmenu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

class MainMenuScreen : Screen {
    @Composable
    override fun Content() {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = "Main Menu")
        }
    }
}