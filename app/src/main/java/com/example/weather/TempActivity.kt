package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TempActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp)
        finish()
    }
}