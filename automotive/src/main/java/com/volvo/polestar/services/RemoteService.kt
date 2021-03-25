package com.volvo.polestar.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.volvo.polestar.IAidlInterface

class RemoteService : Service() {

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private val binder = object : IAidlInterface.Stub() {

        override fun getPkgName(): String {
            return packageName
        }
    }
}