package com.volvo.polestar

import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.util.Log

class VehicleProperties : CarPropertyManager.CarPropertyEventCallback {
    private val TAG = "akshay"
    override fun onChangeEvent(p0: CarPropertyValue<*>?) {
        if (p0 != null) {
            if (p0.propertyId == VehiclePropertyIds.GEAR_SELECTION) {
                Log.d(TAG, "onChangeEvent:GEAR_SELECTION " + p0.value)
            } else if (p0.propertyId == VehiclePropertyIds.PERF_VEHICLE_SPEED) {
                Log.d(TAG, "onChangeEvent: speed in mps " + p0.value)
            } else if (p0.propertyId == VehiclePropertyIds.IGNITION_STATE) {
                Log.d(TAG, "onChangeEvent: IGNITION_STATE " + p0.value)
            }
        }
    }

    override fun onErrorEvent(p0: Int, p1: Int) {
        TODO("Not yet implemented")
    }
}