package com.example.weather

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TableInfo : RealmObject() {
    @PrimaryKey
    lateinit var cityName: String
    lateinit var countryName: String
    lateinit var statuse: String
    var temperature: Int? = null
    var percent: Int? = null
    var windSpeed: Double? = null
}
