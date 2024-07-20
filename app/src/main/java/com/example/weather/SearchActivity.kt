package com.example.weather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresPermission
import com.example.weather.databinding.ActivitySearchBinding
import io.realm.Realm

class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener{
           if (binding.etCityName.text.toString()=="") {
               Toast.makeText(this, "filed id empty", Toast.LENGTH_SHORT).show()
               return@setOnClickListener
           }
            val intent =Intent(this,ShowActivity::class.java)
            intent.putExtra("city",binding.etCityName.text.toString().trim())
            startActivity(intent)
            finish()


        }

    }


}