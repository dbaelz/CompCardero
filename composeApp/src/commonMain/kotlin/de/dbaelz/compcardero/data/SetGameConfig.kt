package de.dbaelz.compcardero.data

import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.encodeValue
import de.dbaelz.compcardero.data.game.GameConfig
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface SetGameConfig {
    operator fun invoke(gameConfig: GameConfig)
}

class SetGameConfigImpl : SetGameConfig, KoinComponent {
    private val settings: Settings by inject()

    override fun invoke(gameConfig: GameConfig) {
        settings.encodeValue(
            GameConfig.serializer(),
            SettingsKey.GAME_CONFIG.name,
            gameConfig
        )
    }
}