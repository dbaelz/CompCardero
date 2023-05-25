package de.dbaelz.compcardero.ui.cards

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.dbaelz.compcardero.MR
import de.dbaelz.compcardero.color_card_border
import de.dbaelz.compcardero.color_energy
import de.dbaelz.compcardero.color_health
import de.dbaelz.compcardero.color_indicator_background
import de.dbaelz.compcardero.data.GameCard
import de.dbaelz.compcardero.data.PlayerStats
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun Board(
    deckCard: ImageResource,
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
        PlayerView(deckCard, opponent, null, null)

        Spacer(Modifier.height(8.dp))

        if (isPlayerActive) {
            PlayerView(deckCard, player, onCardSelected, onEndTurnClicked)
        } else {
            PlayerView(deckCard, player, null, null)
        }
    }
}

@Composable
private fun PlayerView(
    deckCard: ImageResource,
    playerStats: PlayerStats,
    onCardSelected: ((GameCard) -> Unit)?,
    onEndTurnClicked: (() -> Unit)?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Deck(deckCard = deckCard, numberCards = playerStats.numberDeckCards)

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
            .width(CARD_WIDTH)
            .height(CARD_HEIGHT)
            .clip(RoundedCornerShape(8.dp))
            .border(2.dp, color_card_border, RoundedCornerShape(8.dp))
            .background(color_indicator_background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = playerStats.name,
            modifier = Modifier.fillMaxWidth(0.8f)
                .padding(vertical = 4.dp)
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colors.onPrimary,
            minLines = 1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
        )

        Text(
            "${playerStats.health}",
            color = color_health,
            fontSize = 28.sp
        )

        Text(
            "${playerStats.energy}/${playerStats.energySlots}",
            color = color_energy,
            fontSize = 28.sp
        )

        if (onEndTurnClicked != null) {
            Button(
                modifier = Modifier.fillMaxWidth().height(32.dp),
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