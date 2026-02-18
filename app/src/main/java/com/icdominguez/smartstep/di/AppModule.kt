package com.icdominguez.smartstep.di

import com.icdominguez.smartstep.presentation.screens.home.HomeViewModel
import com.icdominguez.smartstep.presentation.screens.personalsettings.PersonalSettingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::PersonalSettingsViewModel)
    viewModelOf(::HomeViewModel)
}