package com.abdelrahman.raafat.memento.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val DATASTORE_NAME = "onboarding_prefs"

private val Context.dataStore by preferencesDataStore(DATASTORE_NAME)

@Singleton
class OnboardingPreferences
    @Inject
    constructor(
        @ApplicationContext private val context: Context
    ) {
        private val showOnboardingKey = booleanPreferencesKey("show_onboarding")

        val showOnboarding: Flow<Boolean> =
            context.dataStore.data.map { prefs ->
                prefs[showOnboardingKey] ?: true
            }

        suspend fun setShowOnboarding(show: Boolean) {
            context.dataStore.edit { prefs ->
                prefs[showOnboardingKey] = show
            }
        }
    }
