package de.dbaelz.compcardero.ui.endgame

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import de.dbaelz.compcardero.MR
import de.dbaelz.compcardero.color_indicator_background
import de.dbaelz.compcardero.data.PlayerStats
import de.dbaelz.compcardero.ui.endgame.EndGameScreenContract.Event
import de.dbaelz.compcardero.ui.endgame.EndGameScreenContract.Navigation
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

class EndGameScreen(private val winner: PlayerStats, private val loser: PlayerStats) : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { EndGameScreenModel(winner, loser) }

        // TODO: Find a way to do this outside of this composable
        val navigationState by screenModel.navigation.collectAsState(null)
        val navigator = LocalNavigator.currentOrThrow
        when (navigationState) {
            Navigation.Back -> navigator.pop()
            null -> {}
        }

        val state by screenModel.state.collectAsState()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(MR.strings.endgame_title)) },
                    navigationIcon = {
                        IconButton(onClick = { screenModel.sendEvent(Event.BackClicked) }) {
                            Icon(Icons.Filled.ArrowBack, null)
                        }
                    }
                )
            }
        ) {
            BoxWithConstraints {
                val maxImageSize = min(maxWidth, maxHeight) * 0.5f

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
                ) {
                    Trophy(maxImageSize, state.winner.name)

                    StatsRow(state.winner, true)

                    StatsRow(state.loser)
                }
            }
        }
    }
}

@Composable
private fun Trophy(maxSize: Dp, playerName: String) {
    Box(
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(imageResource = MR.images.trophy),
            contentDescription = null,
            modifier = Modifier.size(maxSize).clip(RoundedCornerShape(8.dp)),
        )

        Text(
            text = playerName,
            modifier = Modifier
                .padding(8.dp)
                .width(maxSize)
                .padding(8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color_indicator_background)
                .padding(8.dp)
                .align(Alignment.BottomCenter),
            color = MaterialTheme.colors.onPrimary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun StatsRow(playerStats: PlayerStats, isWinner: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isWinner) MaterialTheme.colors.primary else color_indicator_background),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colors.onPrimary
        ) {
            val modifier = Modifier.padding(8.dp)
            Text(
                text = playerStats.name,
                modifier = modifier.fillMaxWidth(0.4f),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            Text(
                text = "${stringResource(MR.strings.endgame_health)} ${playerStats.health}",
                modifier = modifier,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

            Text(
                text = "${stringResource(MR.strings.endgame_energy)} ${playerStats.energy}",
                modifier = modifier,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}