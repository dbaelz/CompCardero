package de.dbaelz.compcardero.ui.about

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import de.dbaelz.compcardero.MR
import de.dbaelz.compcardero.ui.about.AboutScreenContract.Event
import dev.icerock.moko.resources.compose.stringResource

class AboutScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { AboutScreenModel() }

        // TODO: Find a way to do this outside of this composable
        val navigationState by screenModel.navigation.collectAsState(null)
        val navigator = LocalNavigator.currentOrThrow
        when (navigationState) {
            AboutScreenContract.Navigation.Back -> navigator.pop()
            null -> {}

        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(MR.strings.about_title)) },
                    navigationIcon = {
                        IconButton(onClick = { screenModel.sendEvent(Event.BackClicked) }) {
                            Icon(Icons.Filled.ArrowBack, null)
                        }
                    }
                )
            }
        ) {
            AboutContent(it)
        }
    }
}

@Composable
private fun AboutContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colors.secondary)
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(2.dp, MaterialTheme.colors.onSecondary, RoundedCornerShape(8.dp))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colors.onPrimary
        ) {
            Text(
                text = stringResource(MR.strings.app_name),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(MR.strings.splash_subline),
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

            val githubText = buildAnnotatedString {
                append(stringResource(MR.strings.about_github_text))
                addStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.onPrimary,
                        fontSize = 18.sp,
                        textDecoration = TextDecoration.Underline
                    ),
                    start = 0,
                    end = this.length
                )
            }
            val uriHandler = LocalUriHandler.current
            val githubLink = stringResource(MR.strings.about_github_link)
            ClickableText(
                text = githubText,
                onClick = {
                    uriHandler.openUri(githubLink)
                }
            )
        }
    }
}
