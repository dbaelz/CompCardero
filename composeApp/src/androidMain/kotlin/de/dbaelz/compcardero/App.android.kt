package de.dbaelz.compcardero

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.Navigator
import de.dbaelz.compcardero.ui.splash.SplashScreen
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Napier.base(DebugAntilog())

        setContent { Navigator(SplashScreen()) }
    }
}

actual fun getPlatformName(): String = "Android"