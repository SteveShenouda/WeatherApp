package com.example.weather

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.weather.data.GeoCodeService
import com.example.weather.data.WeatherLocalApi
import com.example.weather.data.WeatherLocalApiImpl
import com.example.weather.data.WeatherLocalDataSource
import com.example.weather.data.WeatherRemoteApi
import com.example.weather.data.WeatherRemoteApiImpl
import com.example.weather.data.WeatherRemoteDataSource
import com.example.weather.data.WeatherRepository
import com.example.weather.data.WeatherService
import com.example.weather.ui.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val Context.weatherDataStore by preferencesDataStore(name = "weather")

val appModule = module {
    viewModelOf(::WeatherViewModel)
    singleOf(::WeatherRepository)
    singleOf(::WeatherRemoteDataSource)
    singleOf(::WeatherRemoteApiImpl) bind WeatherRemoteApi::class
    singleOf(::WeatherLocalDataSource)
    singleOf(::WeatherLocalApiImpl) bind WeatherLocalApi::class
    single { Dispatchers.IO }
    single<WeatherService> {
        get<Retrofit>(named("weather")).create(WeatherService::class.java)
    }
    single<Retrofit>(named("weather")) {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/weather/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<GeoCodeService> {
        get<Retrofit>(named("geocode")).create(GeoCodeService::class.java)
    }
    single<Retrofit>(named("geocode")) {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/geo/1.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single {
        androidContext().weatherDataStore
    }
}