import androidx.compose.ui.window.ComposeUIViewController
import de.dbaelz.compcardero.App
import de.dbaelz.compcardero.di.commonModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    Napier.base(DebugAntilog())

    startKoin {
        modules(commonModule())
    }

    return ComposeUIViewController { App() }
}
