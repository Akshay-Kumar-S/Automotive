package com.volvo.polestar

import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.util.Log

class VehicleProperties(private var iMainView: MainView) :
    CarPropertyManager.CarPropertyEventCallback {
    private val TAG = "akshay"
    override fun onChangeEvent(p0: CarPropertyValue<*>?) {
        if (p0 != null) {
            when (p0.propertyId) {
                VehiclePropertyIds.GEAR_SELECTION -> {
                    Log.d(TAG, "onChangeEvent:GEAR_SELECTION " + p0.value)
                }
                VehiclePropertyIds.PERF_VEHICLE_SPEED -> {
                    //Log.d(TAG, "onChangeEvent: speed in mps " + p0.value)
                    iMainView.updateSpeed(p0.value as Float)
                }
                VehiclePropertyIds.IGNITION_STATE -> {
                    Log.d(TAG, "onChangeEvent: IGNITION_STATE " + p0.value)
                }
            }
        }
    }

    override fun onErrorEvent(p0: Int, p1: Int) {
        TODO("Not yet implemented")
    }
}