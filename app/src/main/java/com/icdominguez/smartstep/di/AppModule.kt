package com.icdominguez.smartstep.di

import com.icdominguez.smartstep.presentation.personalsettings.PersonalSettingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::PersonalSettingsViewModel)
}