package de.dbaelz.compcardero.ui.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import de.dbaelz.compcardero.decks.fantasyGameDeck
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun TeaserCards(widthFraction: Float = 0.75f, alpha: Float = 1.0f) {
    BoxWithConstraints {
        val availableHeight = maxHeight * 0.5f
        val availableWidth = maxWidth * widthFraction

        val widthCard = maxOf(availableHeight.value * (2.0 / 3.0), 1.0)
        val availableElements = maxOf((availableWidth.value / widthCard), 1.0).toInt()

        val images by remember {
            mutableStateOf(fantasyGameDeck.cards.shuffled().take(availableElements - 1))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            val modifier = Modifier.height(availableHeight)
            CardTeaserImage(modifier, fantasyGameDeck.deckCard, alpha)

            images.forEach {
                CardTeaserImage(modifier, it.imageResource, alpha)
            }
        }
    }
}

@Composable
private fun CardTeaserImage(modifier: Modifier, imageResource: ImageResource, alpha: Float) {
    Image(
        painter = painterResource(imageResource = imageResource),
        contentDescription = null,
        modifier = modifier.clip(RoundedCornerShape(8.dp)),
        alpha = alpha
    )
}