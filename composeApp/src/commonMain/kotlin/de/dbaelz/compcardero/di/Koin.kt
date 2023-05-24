package de.dbaelz.compcardero.di

import de.dbaelz.compcardero.data.ValidateGameSettings
import de.dbaelz.compcardero.data.ValidateGameSettingsImpl
import org.koin.dsl.module

fun commonModule() = module {
    single<ValidateGameSettings> { ValidateGameSettingsImpl() }
}