package com.volvo.polestar.interfaces

interface MainView {

    fun updateSpeed(value : Float)
    fun updateGear(value : String)
    fun updateIgnitionState(value : String)
}