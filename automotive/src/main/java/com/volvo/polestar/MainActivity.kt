package com.volvo.polestar

import android.annotation.SuppressLint
import android.car.Car
import android.car.hardware.property.CarPropertyManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.volvo.polestar.interfaces.MainView
import com.volvo.polestar.receivers.VehicleProperties
import com.volvo.polestar.services.AppService
import com.volvo.polestar.utils.AndroidUtil
import com.volvo.polestar.utils.CarUtil
import com.volvo.polestar.utils.Network

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
    private lateinit var carManufacture: TextView
    private lateinit var carModel: TextView
    private lateinit var fuelCapacity: TextView
    private lateinit var batteryCapacity: TextView
    private lateinit var fuelType: TextView
    private lateinit var connectorTypes: TextView
    private lateinit var locationView: TextView
    private lateinit var appsView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initCar()
        initUI()
        updateUI()
        CarUtil.getCarProperties(car, vehicleProperties)
        //startService()
        Log.d(TAG, "onCreate: wifi " + Network.getWifiSSID(this))
    }

    private fun initCar() {
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_AUTOMOTIVE))
            return

        if (::car.isInitialized)
            return

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

        carManufacture = findViewById(R.id.car_manufacture)
        carModel = findViewById(R.id.car_model)
        fuelType = findViewById(R.id.car_fuel_type)
        fuelCapacity = findViewById(R.id.car_fuel_capacity)
        batteryCapacity = findViewById(R.id.car_battery_capacity)
        connectorTypes = findViewById(R.id.car_ev_connectors)

        locationView = findViewById(R.id.car_location)
        appsView = findViewById(R.id.apps_installed)
        hideAndroidApi()
    }

    private fun hideAndroidApi() {
        speedView.visibility = View.GONE
        gearView.visibility = View.GONE
        ignitionView.visibility = View.GONE

        carManufacture.visibility = View.GONE
        carModel.visibility = View.GONE
        fuelType.visibility = View.GONE
        fuelCapacity.visibility = View.GONE
        batteryCapacity.visibility = View.GONE
        connectorTypes.visibility = View.GONE
    }


    @SuppressLint("SetTextI18n")
    private fun updateUI() {
        deviceBrand.text = "Brand: " + AndroidUtil.getDeviceBrand()
        deviceManufacture.text = "Manufacture: " + AndroidUtil.getDeviceManufacture()
        deviceModel.text = "Model: " + AndroidUtil.getDeviceModel()
        deviceVersion.text = "Version: " + AndroidUtil.getDeviceVersion()
        deviceUID.text = "UID: " + AndroidUtil.getDeviceUuid(this)

        val carInfo = CarUtil.getCarInfo(car)
        carManufacture.text = "Car Manufacture: " + carInfo.manufacturer
        carModel.text = "Car Model: " + carInfo.model
        fuelType.text = "Fuel Type: " + carInfo.fuelType
        fuelCapacity.text = "Fuel Capacity: " + carInfo.fuelCapacity.toString() + " ml"
        batteryCapacity.text = "Battery Capacity: " + carInfo.evBatteryCapacity.toString()
        connectorTypes.text = "Ev Connectors: " + carInfo.evConnectorTypes.toString()

        val location = AndroidUtil.getLocation(this)
        locationView.text =
            "Location: latitude: ${location?.latitude}, longitude: ${location?.longitude}, accuracy: ${location?.accuracy}"

        appsView.text = AndroidUtil.getAllInstalledApps(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        car.disconnect()
        val pptMgr = car.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
        pptMgr.unregisterCallback(vehicleProperties)
    }

    @SuppressLint("SetTextI18n")
    override fun updateSpeed(value: Float) {
        speedView.text = "SPEED: " + (value * 18 / 5).toInt().toString() + " kmph"
    }

    @SuppressLint("SetTextI18n")
    override fun updateGear(value: String) {
        gearView.text = "GEAR: $value"
    }

    @SuppressLint("SetTextI18n")
    override fun updateIgnitionState(value: String) {
        ignitionView.text = "IGNITION STATE: $value"
    }

    private fun startService() {
        val intent = Intent(this, AppService::class.java)
        ContextCompat.startForegroundService(this, intent)
    }
}