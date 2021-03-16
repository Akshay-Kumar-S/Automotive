package com.volvo.polestar.receivers

import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import com.volvo.polestar.interfaces.MainView
import com.volvo.polestar.utils.CarUtil

class VehicleProperties(private var iMainView: MainView) :
    CarPropertyManager.CarPropertyEventCallback {
    private val TAG = "akshay"
    override fun onChangeEvent(p0: CarPropertyValue<*>?) {
        if (p0 != null) {
            when (p0.propertyId) {
                VehiclePropertyIds.GEAR_SELECTION -> {
                    //Log.d(TAG, "onChangeEvent:GEAR_SELECTION " + p0.value)
                    iMainView.updateGear(CarUtil.getGear(p0.value.toString().toInt()))
                }
                VehiclePropertyIds.PERF_VEHICLE_SPEED -> {
                    //Log.d(TAG, "onChangeEvent: speed in mps " + p0.value)
                    iMainView.updateSpeed(p0.value as Float)
                }
                VehiclePropertyIds.IGNITION_STATE -> {
                    //Log.d(TAG, "onChangeEvent: IGNITION_STATE " + p0.value)
                    iMainView.updateIgnitionState(
                        CarUtil.getIgnitionState(p0.value.toString().toInt())
                    )
                }
            }
        }
    }

    override fun onErrorEvent(p0: Int, p1: Int) {
        TODO("Not yet implemented")
    }
}