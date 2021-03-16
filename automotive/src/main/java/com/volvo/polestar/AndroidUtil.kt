package com.volvo.polestar

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService


object AndroidUtil {
    const val TAG = "akshay"

    @SuppressLint("QueryPermissionsNeeded")
    fun getAllInstalledApps(context: Context) {
        val pm = context.packageManager
        val apps = pm.getInstalledApplications(0)
        for (app in apps) {
            Log.d(TAG, "getAllInstalledApps:app name: " + app.packageName)
            Log.d(
                TAG,
                "getAllInstalledApps:system app " + (app.flags and ApplicationInfo.FLAG_SYSTEM != 0)
            )
        }
    }

    fun getLocation(context: Context) {
        val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "getLocation:have permission ")
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location != null) {
                val lat: Double = location.latitude
                val long: Double = location.longitude
                val acc: Float = location.accuracy
                Log.d(TAG, "getLocation: lat: $lat, long: $long, accuracy: $acc")
            }
        }
    }

    fun getDeviceBrand() {
        Log.d(TAG, "getDeviceType: " + Build.BRAND)
    }

    fun getDeviceManufacture() {
        Log.d(TAG, "getDeviceManufacture: " + Build.MANUFACTURER)
    }

    fun getDeviceModel() {
        Log.d(TAG, "getDeviceName: " + Build.MODEL)
    }

    fun getDeviceVersion() {
        Log.d(TAG, "getDeviceVersion: " + Build.VERSION.SDK_INT)
    }

    @SuppressLint("HardwareIds")
    fun getDeviceUuid(context: Context) {
        val id = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        Log.d(TAG, "getDeviceUuid: $id")
    }
}