package de.dbaelz.compcardero.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.dbaelz.compcardero.MR
import de.dbaelz.compcardero.color_health_green
import de.dbaelz.compcardero.data.GameCard
import de.dbaelz.compcardero.data.PlayerStats
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun Board(
    player: PlayerStats,
    opponent: PlayerStats,
    isPlayerActive: Boolean,
    onCardSelected: (GameCard) -> Unit,
    onEndTurnClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .padding(4.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        PlayerView(opponent, null, null)

        Spacer(Modifier.height(8.dp))

        if (isPlayerActive) {
            PlayerView(player, onCardSelected, onEndTurnClicked)
        } else {
            PlayerView(player, null, null)
        }
    }
}

@Composable
private fun PlayerView(
    playerStats: PlayerStats,
    onCardSelected: ((GameCard) -> Unit)?,
    onEndTurnClicked: (() -> Unit)?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Deck(numberCards = playerStats.numberDeckCards)

        HandCards(
            modifier = Modifier.weight(1f),
            hand = playerStats.hand,
            onCardSelected = onCardSelected
        )

        PlayerStatsView(playerStats = playerStats, onEndTurnClicked = onEndTurnClicked)
    }
}

@Composable
private fun HandCards(
    modifier: Modifier = Modifier,
    hand: List<GameCard>,
    onCardSelected: ((GameCard) -> Unit)?
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.Bottom
    ) {

        hand.forEach {
            Card(gameCard = it, onCardSelected = onCardSelected)
        }
    }
}

@Composable
private fun PlayerStatsView(playerStats: PlayerStats, onEndTurnClicked: (() -> Unit)?) {
    Column(
        modifier = Modifier
            .width(120.dp)
            .height(168.dp)
            .background(Color.LightGray)
            .border(4.dp, Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Spacer(Modifier.height(8.dp))
        Text(
            "${playerStats.health}",
            color = color_health_green,
            fontSize = 32.sp
        )

        Text(
            "${playerStats.energy}/${playerStats.energySlots}",
            color = Color.Blue,
            fontSize = 32.sp
        )

        if (onEndTurnClicked != null) {
            Button(
                modifier = Modifier.padding(12.dp).fillMaxWidth().height(32.dp),
                onClick = onEndTurnClicked
            ) {
                Text(
                    text = stringResource(MR.strings.game_end_turn),
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = MaterialTheme.colors.onPrimary,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
                )
            }
        } else {
            Spacer(Modifier.height(32.dp))
        }
    }
}