package com.icdominguez.smartstep.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.icdominguez.smartstep.data.MeasurementRepositoryImpl
import com.icdominguez.smartstep.data.StepCounterManager
import com.icdominguez.smartstep.data.UserSettingsDataStore
import com.icdominguez.smartstep.domain.MeasurementRepository
import com.icdominguez.smartstep.domain.UserSettings
import com.icdominguez.smartstep.presentation.MainViewModel
import com.icdominguez.smartstep.presentation.screens.home.HomeViewModel
import com.icdominguez.smartstep.presentation.screens.personalsettings.PersonalSettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private const val SMART_STEP_PREFERENCES = "smart_step_preferences"

val appModule = module {

    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = { androidContext().preferencesDataStoreFile(SMART_STEP_PREFERENCES) }
        )
    }

    single<UserSettings> {
        UserSettingsDataStore(dataStore = get())
    }

    single<MeasurementRepository> {
        MeasurementRepositoryImpl()
    }

    single {
        StepCounterManager(androidContext())
    }

    viewModelOf(::MainViewModel)
    viewModelOf(::PersonalSettingsViewModel)
    viewModelOf(::HomeViewModel)
}