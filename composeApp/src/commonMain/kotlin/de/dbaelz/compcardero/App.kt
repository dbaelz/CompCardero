package de.dbaelz.compcardero

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import de.dbaelz.compcardero.ui.splash.SplashScreen

@Composable
internal fun App() = AppTheme {
    Navigator(SplashScreen())
}
expect fun getPlatformName(): String