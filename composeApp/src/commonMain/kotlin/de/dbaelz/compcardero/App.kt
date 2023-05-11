package de.dbaelz.compcardero

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun App() = AppTheme {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(MR.strings.splash_greeting) + getPlatformName(),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp
        )
    }
}

expect fun getPlatformName(): String