package com.example.weather

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.weather.databinding.ActivityMainBinding
import io.realm.Realm
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createRecyclerView()

        binding.btnSearch.setOnClickListener {
            val fileName= "myFile"
            val fileBody= "body"
            val file =File(Environment.getExternalStorageDirectory(),"aab")
            if (!file.exists()){
                file.mkdir()
            }
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }


    }

    @SuppressLint("WrongConstant")
    override fun onResume() {
        super.onResume()
        val refresh = UserDAO().readAll()
        refresh.forEach {
            refresh(it.cityName)
        }
    }

    private fun refresh(cityName: String) {
        val api =
            "https://api.openweathermap.org/data/2.5/weather?q=$cityName&apiKey=91f9810bd3faceeae018e59b663ff3a6"
        val request = JsonObjectRequest(Request.Method.GET,
            api, null,
            {

                val city = it.getString("name")
                val country = it.getJSONObject("sys").getString("country")
                val temp = it.getJSONObject("main").getDouble("temp")
                val wind = it.getJSONObject("wind").getDouble("speed")
                val percent = it.getJSONObject("main").getInt("humidity")
                val main = it.getJSONArray("weather").getJSONObject(0).getString("main")

                val cTemp = (temp - 273).toInt()

                val tableInfo = TableInfo()
                tableInfo.cityName = city
                tableInfo.countryName = country
                tableInfo.temperature = cTemp
                tableInfo.statuse = main
                tableInfo.windSpeed = wind
                tableInfo.percent = percent
                UserDAO().updateUser(tableInfo)

                createRecyclerView()
            }, {


            })
        request.retryPolicy = DefaultRetryPolicy(10000, 1, 1f)
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)
    }

    @SuppressLint("WrongConstant")
    private fun createRecyclerView() {
        val recyclerList = arrayListOf<MyData>()
        val result = UserDAO().readAll()
        try {
            result.forEach {
                recyclerList.add(
                    MyData(
                        it.cityName, it.countryName, it.statuse, it.temperature,
                        it.percent!!, it.windSpeed!!
                    )
                )
            }
            if (recyclerList.isEmpty()){
                binding.MainTxtNothing.visibility= View.VISIBLE
            }else {
                binding.MainTxtNothing.visibility=View.INVISIBLE}

            binding.recyclerView.layoutManager =
                GridLayoutManager(this, 2, GridLayout.VERTICAL, false)
            val adapter = RecyclerAdapter(recyclerList)
            binding.recyclerView.adapter = adapter
            adapter.setOnItemClickListener(object : RecyclerAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@MainActivity, ShowActivity::class.java)
                    intent.putExtra("city", recyclerList[position].cityName)
                    startActivity(intent)
                }

            })
            adapter.setOnItemLongClickListener(object : RecyclerAdapter.OnItemLongListener {
                override fun onItemLongClick(position: Int) {
                    createAlert(position,recyclerList)

                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createAlert(position: Int,list:ArrayList<MyData>){
        val alertDialog=AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("do you want delete ${list[position].cityName}")
            .setPositiveButton("yes") { _, _ ->
                UserDAO().delete(list[position].cityName)
                createRecyclerView()
            }
            .setNegativeButton("no") { _: DialogInterface, _: Int -> }
        alertDialog.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()

        UserDAO().closeDB()
    }



}