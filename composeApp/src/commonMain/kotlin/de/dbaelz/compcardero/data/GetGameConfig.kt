package de.dbaelz.compcardero.data

import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValue
import de.dbaelz.compcardero.data.game.GameConfig
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface GetGameConfig {
    operator fun invoke(): GameConfig
}

class GetGameConfigImpl : GetGameConfig, KoinComponent {
    private val settings: Settings by inject()

    override fun invoke(): GameConfig {
        return settings.decodeValue(
            GameConfig.serializer(),
            SettingsKey.GAME_CONFIG.name,
            GameConfig()
        )
    }
}