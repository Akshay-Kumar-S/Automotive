package com.volvo.polestar.utils

import android.car.Car
import android.car.CarInfoManager
import android.car.VehicleGear
import android.car.VehiclePropertyIds
import android.car.hardware.CarSensorEvent
import android.car.hardware.property.CarPropertyManager
import android.car.hardware.property.CarPropertyManager.SENSOR_RATE_FASTEST
import android.car.hardware.property.CarPropertyManager.SENSOR_RATE_NORMAL
import android.util.Log
import com.volvo.polestar.models.CarInfo


object CarUtil {
    const val TAG = "akshay"

    fun getCarInfo(car: Car): CarInfo {
        val info = car.getCarManager(Car.INFO_SERVICE) as CarInfoManager
        Log.d(TAG, "getCarInfo:manufacturer " + info.manufacturer)
        Log.d(TAG, "getCarInfo:model " + info.model)
        //Log.d(TAG, "getCarInfo:modelYearInInteger " + info.modelYearInInteger)
        Log.d(TAG, "getCarInfo:fuelCapacity " + info.fuelCapacity)
        Log.d(TAG, "getCarInfo:evBatteryCapacity " + info.evBatteryCapacity)
        //Log.d(TAG, "getCarInfo:driverSeat " + info.driverSeat)
        //Log.d(TAG, "getCarInfo:evPortLocation " + info.evPortLocation)
        //Log.d(TAG, "getCarInfo:fuelDoorLocation " + info.fuelDoorLocation)
        val carInfo =
            CarInfo(info.manufacturer, info.model, info.fuelCapacity, info.evBatteryCapacity)
        carInfo.fuelType = getFuelTypes(info.fuelTypes)
        carInfo.evConnectorTypes = getEvConnectorTypes(info.evConnectorTypes)

        return carInfo
    }

    private fun getFuelTypes(list: IntArray): String {
        for (i in list) {
            return when (i) {
                1 -> "UNLEADED"
                2 -> "LEADED"
                3 -> "DIESEL_1"
                4 -> "DIESEL_2"
                5 -> "BIODIESEL"
                6 -> "E85"
                7 -> "LPG"
                8 -> "CNG"
                9 -> "LNG"
                10 -> "ELECTRIC"
                11 -> "HYDROGEN"
                12 -> "OTHER"
                else -> "UNKNOWN"
            }
        }
        return "UNKNOWN"
    }

    private fun getEvConnectorTypes(list: IntArray): ArrayList<String> {
        val typeList: ArrayList<String> = ArrayList()
        for (i in list) {
            when (i) {
                1 -> typeList.add("J1772")
                2 -> typeList.add("MENNEKES")
                3 -> typeList.add("SCAME")
                4 -> typeList.add("CHADEMO")
                5 -> typeList.add("COMBO_1")
                6 -> typeList.add("COMBO_2")
                7 -> typeList.add("TESLA_ROADSTER")
                8 -> typeList.add("TESLA_HPWC")
                9 -> typeList.add("TESLA_SUPERCHARGER")
                10 -> typeList.add("GBT")
                11 -> typeList.add("GBT_DC")
                101 -> typeList.add("OTHER")
                else -> typeList.add("UNKNOWN")
            }
        }
        return typeList
    }

    fun getGear(gear: Int): String {
        return when (gear) {
            VehicleGear.GEAR_PARK -> "PARK"
            VehicleGear.GEAR_DRIVE -> "DRIVE"
            VehicleGear.GEAR_NEUTRAL -> "NEUTRAL"
            VehicleGear.GEAR_REVERSE -> "REVERSE"
            VehicleGear.GEAR_FIRST -> "1"
            VehicleGear.GEAR_SECOND -> "2"
            VehicleGear.GEAR_THIRD -> "3"
            VehicleGear.GEAR_FOURTH -> "4"
            VehicleGear.GEAR_FIFTH -> "5"
            VehicleGear.GEAR_SIXTH -> "6"
            VehicleGear.GEAR_SEVENTH -> "7"
            VehicleGear.GEAR_EIGHTH -> "8"
            VehicleGear.GEAR_NINTH -> "9"
            else -> "Unknown"
        }
    }

    @Suppress("DEPRECATION")
    public fun getIgnitionState(state: Int): String {
        return when (state) {
            CarSensorEvent.IGNITION_STATE_UNDEFINED -> "UNDEFINED"
            CarSensorEvent.IGNITION_STATE_LOCK -> "LOCK"
            CarSensorEvent.IGNITION_STATE_OFF -> "OFF"
            CarSensorEvent.IGNITION_STATE_ACC -> "ACC"
            CarSensorEvent.IGNITION_STATE_ON -> "ON"
            CarSensorEvent.IGNITION_STATE_START -> "START"
            else -> "Unknown"
        }
    }

    fun getCarProperties(car: Car, iCallback: CarPropertyManager.CarPropertyEventCallback) {
        val pptMgr = car.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager

        pptMgr.registerCallback(iCallback, VehiclePropertyIds.GEAR_SELECTION, SENSOR_RATE_NORMAL)
        pptMgr.registerCallback(
            iCallback,
            VehiclePropertyIds.PERF_VEHICLE_SPEED,
            SENSOR_RATE_FASTEST
        )
        pptMgr.registerCallback(iCallback, VehiclePropertyIds.IGNITION_STATE, SENSOR_RATE_FASTEST)
    }
}