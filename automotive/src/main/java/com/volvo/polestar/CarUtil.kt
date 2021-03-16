package com.volvo.polestar

import android.app.Service
import android.car.Car
import android.car.CarInfoManager
import android.car.VehiclePropertyIds
import android.car.content.pm.CarPackageManager
import android.car.hardware.CarPropertyValue
import android.car.hardware.CarSensorEvent
import android.car.hardware.property.CarPropertyManager
import android.car.hardware.property.CarPropertyManager.SENSOR_RATE_FASTEST
import android.car.hardware.property.CarPropertyManager.SENSOR_RATE_NORMAL
import android.util.Log


object CarUtil {
    const val TAG = "akshay"

    fun getCarInfo(car: Car) {
        val info = car.getCarManager(Car.INFO_SERVICE) as CarInfoManager
        Log.d(TAG, "getCarInfo:manufacturer " + info.manufacturer)
        Log.d(TAG, "getCarInfo:model " + info.model)
        //Log.d(TAG, "getCarInfo:modelYearInInteger " + info.modelYearInInteger)
        Log.d(TAG, "getCarInfo:fuelCapacity " + info.fuelCapacity)
        listFuelTypes(info.fuelTypes)
        Log.d(TAG, "getCarInfo:evBatteryCapacity " + info.evBatteryCapacity)
        listEvConnectorTypes(info.evConnectorTypes)
        //Log.d(TAG, "getCarInfo:driverSeat " + info.driverSeat)
        //Log.d(TAG, "getCarInfo:evPortLocation " + info.evPortLocation)
        //Log.d(TAG, "getCarInfo:fuelDoorLocation " + info.fuelDoorLocation)
    }

    private fun listFuelTypes(list: IntArray) {
        for (i in list) {
            when (i) {
                1 -> Log.d(TAG, "fuelType: fuelTypeFuelType.UNLEADED")
                2 -> Log.d(TAG, "fuelType: fuelTypeFuelType.LEADED")
                3 -> Log.d(TAG, "fuelType: fuelTypeFuelType.DIESEL_1")
                4 -> Log.d(TAG, "fuelType: fuelTypeFuelType.DIESEL_2")
                5 -> Log.d(TAG, "fuelType: fuelTypeFuelType.BIODIESEL")
                6 -> Log.d(TAG, "fuelType: fuelTypeFuelType.E85")
                7 -> Log.d(TAG, "fuelType: fuelTypeFuelType.LPG")
                8 -> Log.d(TAG, "fuelType: fuelTypeFuelType.CNG")
                9 -> Log.d(TAG, "fuelType: fuelTypeFuelType.LNG")
                10 -> Log.d(TAG, "fuelType: fuelTypeFuelType.ELECTRIC")
                11 -> Log.d(TAG, "fuelType: fuelTypeFuelType.HYDROGEN")
                12 -> Log.d(TAG, "fuelType: fuelTypeFuelType.OTHER")
                else -> Log.d(TAG, "fuelType: fuelTypeFuelType.UNKNOWN")
            }
        }
    }

    private fun listEvConnectorTypes(list: IntArray) {
        for (i in list) {
            when (i) {
                1 -> Log.d(TAG, "connectorType: fuelTypeEvConnectorType.J1772")
                2 -> Log.d(TAG, "connectorType: fuelTypeEvConnectorType.MENNEKES")
                3 -> Log.d(TAG, "connectorType: fuelTypeEvConnectorType.SCAME")
                4 -> Log.d(TAG, "connectorType: fuelTypeEvConnectorType.CHADEMO")
                5 -> Log.d(TAG, "connectorType: fuelType EvConnectorType.COMBO_1")
                6 -> Log.d(TAG, "connectorType: fuelTypeEvConnectorType.COMBO_2")
                7 -> Log.d(TAG, "connectorType: fuelTypeEvConnectorType.TESLA_ROADSTER")
                8 -> Log.d(TAG, "connectorType: fuelTypeEvConnectorType.TESLA_HPWC")
                9 -> Log.d(TAG, "connectorType: fuelTypeEvConnectorType.TESLA_SUPERCHARGER")
                10 -> Log.d(TAG, "connectorType: fuelTypeEvConnectorType.GBT")
                11 -> Log.d(TAG, "connectorType: fuelTypeEvConnectorType.GBT_DC")
                101 -> Log.d(TAG, "connectorType: fuelTypeEvConnectorType.OTHER")
                else -> Log.d(TAG, "connectorType: fuelType EvConnectorType.UNKNOWN")
            }
        }
    }

    fun getCarProperties(car: Car, iCallback: CarPropertyManager.CarPropertyEventCallback) {
        val pptMgr = car.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager

        pptMgr.registerCallback(iCallback, VehiclePropertyIds.GEAR_SELECTION ,SENSOR_RATE_NORMAL)
        pptMgr.registerCallback(iCallback, VehiclePropertyIds.PERF_VEHICLE_SPEED , SENSOR_RATE_FASTEST)
        pptMgr.registerCallback(iCallback, VehiclePropertyIds.IGNITION_STATE , SENSOR_RATE_FASTEST)
    }
}