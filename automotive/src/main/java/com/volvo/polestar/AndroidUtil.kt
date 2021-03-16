package com.volvo.polestar

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log

object AndroidUtil {
    const val TAG = "akshay"

    @SuppressLint("QueryPermissionsNeeded")
    fun getAllInstalledApps(context: Context) {
        val pm = context.packageManager
        val apps = pm.getInstalledApplications(0)
        for (app in apps) {
            Log.d(TAG, "getAllInstalledApps:app name: " + app.packageName)
            Log.d(TAG, "getAllInstalledApps:system app " + (app.flags and  ApplicationInfo.FLAG_SYSTEM  != 0))
        }
    }
}