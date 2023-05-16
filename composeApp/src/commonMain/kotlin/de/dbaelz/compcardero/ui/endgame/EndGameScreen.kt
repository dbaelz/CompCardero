package de.dbaelz.compcardero.ui.endgame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import de.dbaelz.compcardero.MR
import de.dbaelz.compcardero.data.PlayerStats
import dev.icerock.moko.resources.compose.stringResource

class EndGameScreen(private val winner: PlayerStats, private val loser: PlayerStats) : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { EndGameScreenModel(winner, loser) }

        // TODO: Find a way to do this outside of this composable
        val navigationState by screenModel.navigation.collectAsState(null)
        /*        when (navigationState) {
                    null -> {}
                }*/

        val state by screenModel.state.collectAsState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            StatsRow(winner, true)

            StatsRow(loser)
        }
    }
}

@Composable
private fun StatsRow(playerStats: PlayerStats, isWinner: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(0.6f)
            .background(if (isWinner) Color.Green else Color.Gray),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides Color.Black
        ) {
            Text(
                text = playerStats.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            Text(
                text = "${stringResource(MR.strings.endgame_health)} ${playerStats.health}",
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

            Text(
                text = "${stringResource(MR.strings.endgame_energy)} ${playerStats.energy}",
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}