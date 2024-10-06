package com.pp.weatherandroid.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.pp.weatherandroid.R
import timber.log.Timber

class LocationHelper(private val context: Context, private val locationCallback: LocationCallback) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private var isPermissionDenied: Boolean = false

    private val requestPermissionLauncher =
        (context as AppCompatActivity)
            .registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    getKnownLocation()
                } else {
                    isPermissionDenied = true
                    Timber.e("Location permission denied")
                    return@registerForActivityResult
                }
            }

    fun requestLocation() {
        if (!isPermissionDenied) {
            if (isLocationEnabled()) {
                getKnownLocation()
            } else {
                showLocationSettingsDialog()
            }
        }
    }

    private fun getKnownLocation() {
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    location?.let {
                        locationCallback.onLocationReceived(it)
                    } ?: run {
                        Timber.e("Location not available")
                    }
                }.addOnFailureListener { e ->
                    Timber.e(e, "Error getting location")
                }
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun showLocationSettingsDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.enabled_gps)
            .setMessage(R.string.gps_settings_message)
            .setPositiveButton(R.string.gps_settings_button_text) { _, _ ->
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton(R.string.gps_settings_cancel_button_text) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    interface LocationCallback {
        fun onLocationReceived(location: Location)
    }
}