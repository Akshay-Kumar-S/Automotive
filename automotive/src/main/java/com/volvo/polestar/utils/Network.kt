package com.volvo.polestar.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Context.WIFI_SERVICE
import android.net.ConnectivityManager
import android.net.wifi.WifiManager


object Network {

    fun getWifiSSID(context: Context) : String {
        val wifiManager = context.getSystemService(WIFI_SERVICE) as WifiManager
        val info = wifiManager.connectionInfo
        return info.ssid
    }
}