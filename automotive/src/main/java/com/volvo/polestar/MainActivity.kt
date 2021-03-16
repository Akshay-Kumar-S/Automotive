package com.volvo.polestar

import android.car.Car
import android.car.hardware.property.CarPropertyManager
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var car: Car
    private lateinit var vehicleProperties: VehicleProperties
    private var TAG = "akshay"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initCar()
        Log.e(TAG, "onCreate: CarInfo")
        CarUtil.getCarInfo(car)
        Log.e(TAG, "onCreate: getCarProperties")
        CarUtil.getCarProperties(car, vehicleProperties)

        AndroidUtil.getAllInstalledApps(this)
        AndroidUtil.getLocation(this)
        AndroidUtil.getDeviceBrand()
        AndroidUtil.getDeviceManufacture()
        AndroidUtil.getDeviceModel()
        AndroidUtil.getDeviceVersion()
        AndroidUtil.getDeviceUuid(this)
    }

    private fun initCar() {
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_AUTOMOTIVE)) {
            return
        }

        if (::car.isInitialized) {
            return
        }

        car = Car.createCar(this)
        vehicleProperties = VehicleProperties()
    }

    override fun onDestroy() {
        super.onDestroy()
        car.disconnect()
        val pptMgr = car.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
        pptMgr.unregisterCallback(vehicleProperties)
    }

}