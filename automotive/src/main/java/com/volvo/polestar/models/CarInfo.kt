package com.volvo.polestar.models

data class CarInfo(

    var manufacturer : String,
    var model : String?,
    var fuelCapacity : Float,
    var evBatteryCapacity : Float,
) {
    lateinit var fuelType : String
    var evConnectorTypes : ArrayList<String> = ArrayList()
}
