package com.volvo.polestar.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat


object AndroidUtil {
    const val TAG = "akshay"

    @SuppressLint("QueryPermissionsNeeded")
    fun getAllInstalledApps(context: Context): String {
        val pm = context.packageManager
        val apps = pm.getInstalledApplications(0)
        var appsList = "Installed Applications \n"
        for (app in apps) {
            appsList += app.packageName + "\n"
            appsList += getAppType(app) + "\n"
            /*Log.d(TAG, "getAllInstalledApps:app name: " + app.packageName)
            Log.d(
                TAG,
                "getAllInstalledApps:system app " + (app.flags and ApplicationInfo.FLAG_SYSTEM != 0)
            )*/
        }
        return appsList
    }

    private fun getAppType(applicationInfo: ApplicationInfo): String {
        return if (applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0)
            "System"
        else
            "User"
    }

    fun getLocation(context: Context): Location? {
        val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        }
        return null
    }

    fun getDeviceBrand(): String {
        return Build.BRAND
    }

    fun getDeviceManufacture(): String {
        return Build.MANUFACTURER
    }

    fun getDeviceModel(): String {
        return Build.MODEL
    }

    fun getDeviceVersion(): String {
        return Build.VERSION.SDK_INT.toString()
    }

    @SuppressLint("HardwareIds")
    fun getDeviceUuid(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}