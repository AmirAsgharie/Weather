package com.example.weather

import android.util.Log
import android.widget.Toast
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.createObject
import kotlin.math.log

class UserDAO {

    private val realm=Realm.getDefaultInstance()


    fun readAll():RealmResults<TableInfo> =realm.where(TableInfo::class.java).findAll()

    fun readByName(cityName:String):TableInfo? = realm.where(TableInfo::class.java).equalTo("cityName",cityName).findFirst()

    fun updateUser(tableInfo:TableInfo){
        realm.executeTransaction{
            it.copyToRealmOrUpdate(tableInfo)
        }
    }

    fun delete(cityName: String){
        realm.executeTransaction(){
            val deleteCity=readByName(cityName)
            deleteCity?.deleteFromRealm()
        }
    }


    fun closeDB(){
        realm.close()
    }
}