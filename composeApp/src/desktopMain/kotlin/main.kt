import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import de.dbaelz.compcardero.App
import de.dbaelz.compcardero.MR
import de.dbaelz.compcardero.di.commonModule
import dev.icerock.moko.resources.compose.stringResource
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.context.startKoin

fun main() = application {
    Napier.base(DebugAntilog())

    startKoin {
        modules(commonModule())
    }

    Window(
        title = stringResource(MR.strings.app_name),
        state = rememberWindowState(width = 1024.dp, height = 768.dp),
        onCloseRequest = ::exitApplication,
    ) { App() }
}