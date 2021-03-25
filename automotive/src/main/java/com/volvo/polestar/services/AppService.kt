package com.volvo.polestar.services

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.volvo.polestar.MainActivity
import com.volvo.polestar.R

class AppService : Service() {
    private val TAG = "akshay"

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotification()
        startLog()
        return START_STICKY
    }

    private fun startLog() {
        while (true) {
            Log.d(TAG, "onStartCommand: ")
        }
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onDestroy() {
        stopForeground(true)
        super.onDestroy()
    }

    private fun showNotification() {
        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        val notification: Notification = Notification.Builder(this, packageName)
            .setContentTitle("Notification title")
            .setContentText("Notification content")
            .setSmallIcon(R.mipmap.ic_app_launcher)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
    }
}