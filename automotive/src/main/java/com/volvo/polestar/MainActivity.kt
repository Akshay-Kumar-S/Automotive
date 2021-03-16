package com.volvo.polestar

import android.annotation.SuppressLint
import android.car.Car
import android.car.hardware.property.CarPropertyManager
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.volvo.polestar.interfaces.MainView
import com.volvo.polestar.receivers.VehicleProperties
import com.volvo.polestar.utils.AndroidUtil
import com.volvo.polestar.utils.CarUtil

class MainActivity : AppCompatActivity(), MainView {
    private var TAG = "akshay"
    private lateinit var car: Car
    private lateinit var vehicleProperties: VehicleProperties

    private lateinit var deviceBrand: TextView
    private lateinit var deviceManufacture: TextView
    private lateinit var deviceModel: TextView
    private lateinit var deviceVersion: TextView
    private lateinit var deviceUID: TextView
    private lateinit var speedView: TextView
    private lateinit var gearView: TextView
    private lateinit var ignitionView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initCar()
        initUI()
        updateUI()
        Log.e(TAG, "onCreate: CarInfo")
        CarUtil.getCarInfo(car)
        Log.e(TAG, "onCreate: getCarProperties")
        CarUtil.getCarProperties(car, vehicleProperties)

        AndroidUtil.getAllInstalledApps(this)
        AndroidUtil.getLocation(this)
    }

    private fun initCar() {
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_AUTOMOTIVE)) {
            return
        }

        if (::car.isInitialized) {
            return
        }

        car = Car.createCar(this)
        vehicleProperties = VehicleProperties(this)
    }

    private fun initUI() {
        deviceBrand = findViewById(R.id.device_brand)
        deviceManufacture = findViewById(R.id.device_manufacture)
        deviceModel = findViewById(R.id.device_model)
        deviceVersion = findViewById(R.id.device_version)
        deviceUID = findViewById(R.id.device_uid)
        speedView = findViewById(R.id.speed_view)
        gearView = findViewById(R.id.gear_view)
        ignitionView = findViewById(R.id.ignition_view)
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI() {
        deviceBrand.text = "Brand: "+ AndroidUtil.getDeviceBrand()
        deviceManufacture.text = "Manufacture: "+ AndroidUtil.getDeviceManufacture()
        deviceModel.text = "Model: "+ AndroidUtil.getDeviceModel()
        deviceVersion.text = "Version: "+ AndroidUtil.getDeviceVersion()
        deviceUID.text = "UID: "+ AndroidUtil.getDeviceUuid(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        car.disconnect()
        val pptMgr = car.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
        pptMgr.unregisterCallback(vehicleProperties)
    }

    @SuppressLint("SetTextI18n")
    override fun updateSpeed(value: Float) {
        speedView.text = "SPEED : " + (value * 18 / 5).toInt().toString() + " kmph"
    }

    @SuppressLint("SetTextI18n")
    override fun updateGear(value: String) {
        gearView.text = "GEAR : $value"
    }

    @SuppressLint("SetTextI18n")
    override fun updateIgnitionState(value: String) {
        ignitionView.text = "IGNITION STATE : $value"
    }
}