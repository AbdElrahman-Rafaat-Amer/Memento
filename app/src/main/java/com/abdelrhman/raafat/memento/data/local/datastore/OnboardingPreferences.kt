package com.abdelrhman.raafat.memento.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton
import javax.inject.Inject

private const val DATASTORE_NAME = "onboarding_prefs"

private val Context.dataStore by preferencesDataStore(DATASTORE_NAME)

@Singleton
class OnboardingPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val SHOW_ONBOARDING = booleanPreferencesKey("show_onboarding")

    val showOnboarding: Flow<Boolean> =
        context.dataStore.data.map { prefs ->
            prefs[SHOW_ONBOARDING] ?: true
        }

    suspend fun setShowOnboarding(show: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[SHOW_ONBOARDING] = show
        }
    }

    suspend fun resetOnboarding() {
        setShowOnboarding(true)
    }
}
