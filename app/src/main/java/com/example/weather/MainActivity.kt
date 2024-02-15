package com.example.weather

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.example.weather.ui.WeatherFragment

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Retrieve the NavController.
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Add the graph to the NavController with `createGraph()`.
        navController.graph = navController.createGraph(
            startDestination = "weather"
        ) {
            // Associate each destination with one of the route constants.
            fragment<WeatherFragment>("weather") {
                label = "Weather"
            }
        }
    }
}