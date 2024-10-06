package com.pp.weatherandroid

import android.location.Location
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.pp.weatherandroid.ui.branding.WeatherAndroidTheme
import com.pp.weatherandroid.ui.compose.WeatherScreen
import com.pp.weatherandroid.utils.LocationHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), LocationHelper.LocationCallback {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var locationHelper: LocationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAndroidTheme {
                WeatherScreen()
            }
        }

        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        locationHelper = LocationHelper(this, this)
    }

    override fun onResume() {
        super.onResume()
        locationHelper.requestLocation()
    }

    override fun onLocationReceived(location: Location) {
        viewModel.getCityNameFromLocation(location)
    }
}