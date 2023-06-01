package de.dbaelz.compcardero.di

import com.russhwolf.settings.Settings
import de.dbaelz.compcardero.data.GetGameConfig
import de.dbaelz.compcardero.data.GetGameConfigImpl
import de.dbaelz.compcardero.data.GetGameDecks
import de.dbaelz.compcardero.data.GetGameDecksImpl
import de.dbaelz.compcardero.data.ValidateGameConfiguration
import de.dbaelz.compcardero.data.ValidateGameConfigurationImpl
import de.dbaelz.compcardero.decks.fantasyGameDeck
import org.koin.dsl.module

fun commonModule() = module {
    single<ValidateGameConfiguration> { ValidateGameConfigurationImpl() }

    single<GetGameConfig> { GetGameConfigImpl() }

    single<GetGameDecks> { GetGameDecksImpl(listOf(fantasyGameDeck)) }

    single { Settings() }
}