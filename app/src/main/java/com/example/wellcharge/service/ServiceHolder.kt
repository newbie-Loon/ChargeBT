package com.example.wellcharge.service

object ServiceHolder {
    private var myService: BTKeepConnService? = null

    fun setService(service: BTKeepConnService?) {
        myService = service
    }

    fun getService(): BTKeepConnService? {
        return myService
    }

    fun clearService(){
        myService = null
    }
}