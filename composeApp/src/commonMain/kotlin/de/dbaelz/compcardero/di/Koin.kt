package de.dbaelz.compcardero.di

import com.russhwolf.settings.Settings
import de.dbaelz.compcardero.data.ValidateGameConfiguration
import de.dbaelz.compcardero.data.ValidateGameConfigurationImpl
import org.koin.dsl.module

fun commonModule() = module {
    single<ValidateGameConfiguration> { ValidateGameConfigurationImpl() }

    single { Settings() }
}