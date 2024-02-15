package com.example.weather.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeatherLocalApiImpl(private val dataStore: DataStore<Preferences>) : WeatherLocalApi {
    private val LAST_CITY = stringPreferencesKey("last_city")
    override val lastCityFlow: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[LAST_CITY] ?: ""
        }

    override suspend fun saveWeather(city: String) {
        dataStore.edit { preferences ->
            preferences[LAST_CITY] = city
        }
    }
}