package com.pp.weatherandroid.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class LocationToCityMapper @Inject constructor(private val context: Context) {

    fun getCityNameFromLocation(location: Location, callback: (String?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val geocoder = Geocoder(context, Locale.getDefault())
            val cityName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getFromLocationAsync(geocoder, location)
            } else {
                getFromLocationSync(geocoder, location)
            }

            withContext(Dispatchers.Main) {
                callback(cityName)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private suspend fun getFromLocationAsync(geocoder: Geocoder, location: Location): String? {
        return suspendCancellableCoroutine { continuation ->
            geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1,
                object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: MutableList<Address>) {
                        val cityName = if (addresses.isNotEmpty()) {
                            addresses[0].locality
                        } else {
                            null
                        }
                        continuation.resume(cityName, null)
                    }

                    override fun onError(errorMessage: String?) {
                        continuation.resume(null, null)
                    }
                }
            )
        }
    }

    private fun getFromLocationSync(geocoder: Geocoder, location: Location): String? {
        return try {
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses?.isNotEmpty() == true) {
                addresses[0].locality
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}