import androidx.compose.ui.window.ComposeUIViewController
import de.dbaelz.compcardero.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }
}
