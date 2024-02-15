package com.example.weather

import com.example.weather.data.WeatherLocalApi
import com.example.weather.data.WeatherLocalDataSource
import com.example.weather.data.WeatherRemoteApi
import com.example.weather.data.WeatherRemoteDataSource
import com.example.weather.data.WeatherRepository
import com.example.weather.data.models.Clouds
import com.example.weather.data.models.Coord
import com.example.weather.data.models.Main
import com.example.weather.data.models.Sys
import com.example.weather.data.models.Weather
import com.example.weather.data.models.Wind
import com.example.weather.ui.WeatherViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class WeatherViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    object FakeWeatherLocalApi : WeatherLocalApi {
        override val lastCityFlow: Flow<String>
            get() = flow { emit("Sunnyvale") }

        override suspend fun saveWeather(city: String) {

        }
    }

    object FakeWeatherRemoteApi : WeatherRemoteApi {
        private val weather = Weather(
            base = "",
            clouds = Clouds(0),
            cod = 0,
            coord = Coord(0.0, 0.0),
            dt = 0,
            id = 0,
            main = Main(
                feels_like = 0.0,
                humidity = 0,
                pressure = 0,
                temp = 50.0,
                temp_max = 0.0,
                temp_min = 0.0
            ),
            name = "Sunnyvale",
            sys = Sys(country = "", id = 0, sunrise = 0, sunset = 0, type = 0),
            timezone = 0,
            visibility = 0,
            weather = emptyList(),
            wind = Wind(deg = 0, speed = 0.0)
        )

        override suspend fun getWeather(city: String): Weather {
            return weather
        }

        override suspend fun getWeather(lat: String, lon: String): Weather {
            return weather.copy(name = "Santa Clara")
        }
    }
    private val localDataSource = WeatherLocalDataSource(FakeWeatherLocalApi, mainDispatcherRule.testDispatcher)
    private val remoteDataSource = WeatherRemoteDataSource(FakeWeatherRemoteApi, mainDispatcherRule.testDispatcher)
    private val repository = WeatherRepository(remoteDataSource, localDataSource)
    private val viewModel = WeatherViewModel(repository)

    @Test
    fun getWeatherByCity() = runTest {
        viewModel.getWeather("Sunnyvale")
        assertEquals("Sunnyvale", viewModel.uiState.value.weather?.name)
    }

    @Test
    fun getWeatherByLatLon() {
        viewModel.getWeather("37.3468383", "-121.9915033")
        assertEquals("Santa Clara", viewModel.uiState.value.weather?.name)
    }

    @Test
    fun getLastCityWeather() {
        viewModel.getLastCityWeather()
        assertEquals("Sunnyvale", viewModel.uiState.value.weather?.name)
    }
}