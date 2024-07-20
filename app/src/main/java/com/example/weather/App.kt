package com.example.weather

import android.app.Application
import androidx.annotation.RequiresPermission
import io.realm.Realm
import io.realm.RealmConfiguration

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        createRealm()
    }

    private fun createRealm(){
        Realm.init(this)

        val config=RealmConfiguration.Builder()
            .name("InfoDB")
            .schemaVersion(1)
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()

        Realm.setDefaultConfiguration(config)
    }
}