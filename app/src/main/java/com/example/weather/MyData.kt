package com.example.weather

data class MyData(
    val cityName:String,
    val countryName:String,
    val status:String,
    val temperature: Int?,
    val percent:Int,
    val windSpeed:Double)
