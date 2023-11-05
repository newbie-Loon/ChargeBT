package com.example.wellcharge.service

import org.json.JSONObject

object SettingValue {
    private var settingValue : JSONObject? = null
    private var strChipId : String? = null
    private var iBuzzer : Int? = null
    private var iLed : Int? = null
    private var iMode : Int? = null
    private var strStartTime : String? = null
    private var strDuration : String? = null
    private var iTempSetPoint : Int? = null
    private var iMaxCurrentCharging : Int? = null
    private var iChargingCurrent : Int? = null
    private var IChargingState : Int? = null
    private var boolIsTablet : Boolean = false

    fun setSetting(jsonObject : JSONObject) {
        settingValue = jsonObject
        strChipId = jsonObject.getString("strChipId")
        strStartTime = jsonObject.getString("strStartTime")
        strDuration = jsonObject.getString("strDuration")
        iBuzzer = jsonObject.getInt("iBuzzer")
        iLed = jsonObject.getInt("iLed")
        iMode = jsonObject.getInt("iMode")
        iTempSetPoint = jsonObject.getInt("iTempSetPoint")
        iMaxCurrentCharging = jsonObject.getInt("iMaxCurrentCharging")
        iChargingCurrent = jsonObject.getInt("iChargingCurrent")
    }
    fun getSetting(): JSONObject? {
        return settingValue
    }
    fun getStrChipId(): String? {
        return strChipId
    }
    fun getStrStartTime(): String? {
        return strStartTime
    }
    fun getStrDuration(): String? {
        return strDuration
    }
    fun getIBuzzer(): Int? {
        return iBuzzer
    }
    fun getILed(): Int? {
        return iLed
    }
    fun getIMode(): Int? {
        return iMode
    }
    fun getITempSetPoint(): Int? {
        return iTempSetPoint
    }
    fun getIMaxCurrentCharging(): Int? {
        return iMaxCurrentCharging
    }
    fun getIChargingCurrent(): Int? {
        return iChargingCurrent
    }

    fun setStrStartTime(input : String){
        strStartTime = input
    }
    fun setStrDuration(input : String){
        strDuration= input
    }
    fun setIBuzzer(input : Int){
        iBuzzer= input
    }
    fun setILed(input : Int){
        iLed= input
    }
    fun setIMode(input : Int){
        iMode= input
    }
    fun setITempSetPoint(input : Int){
        iTempSetPoint= input
    }
    fun setIChargingCurrent(input : Int){
        iChargingCurrent= input
    }

    fun getIsTablet(): Boolean {
        return boolIsTablet
    }

    fun setIsTablet(isTablet : Boolean){
        boolIsTablet = isTablet
    }


}