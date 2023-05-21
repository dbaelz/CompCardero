package de.dbaelz.compcardero.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.dbaelz.compcardero.color_cards_indicator_background
import de.dbaelz.compcardero.color_health_green
import de.dbaelz.compcardero.data.GameCard
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource

val CARD_WIDTH = 110.dp
val CARD_HEIGHT = 165.dp

@Composable
fun Card(modifier: Modifier = Modifier, gameCard: GameCard, onCardSelected: ((GameCard) -> Unit)?) {
    Box(
        modifier = modifier
            .width(CARD_WIDTH)
            .height(CARD_HEIGHT)
            .clip(RoundedCornerShape(8.dp))
            .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
            .then(if (onCardSelected != null) Modifier.clickable { onCardSelected(gameCard) } else Modifier)
    ) {
        Image(
            painter = painterResource(imageResource = gameCard.imageResource),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(4.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {
            IndicatorLabel(gameCard.energyCost, CircleShape, Color.Blue)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IndicatorLabel(gameCard.attack, CutCornerShape(8.dp), Color.Red)

                IndicatorLabel(gameCard.heal, CutCornerShape(16.dp), color_health_green)
            }
        }

    }
}

@Composable
private fun IndicatorLabel(value: Int, shape: Shape, color: Color) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(shape)
            .border(2.dp, color, shape)
            .background(color_cards_indicator_background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value.toString(),
            color = MaterialTheme.colors.onPrimary,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Deck(modifier: Modifier = Modifier, deckCard: ImageResource, numberCards: Int) {
    Box(
        modifier = modifier
            .width(CARD_WIDTH)
            .height(CARD_HEIGHT)
            .clip(RoundedCornerShape(8.dp))
            .border(2.dp, Color.Black, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(deckCard),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
        )

        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color.DarkGray)
                .border(2.dp, Color.Black, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = numberCards.toString(),
                color = MaterialTheme.colors.onPrimary,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}