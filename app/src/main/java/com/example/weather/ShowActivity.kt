package com.example.weather

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.weather.databinding.ActivityShowBinding
import java.util.*

class ShowActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowBinding

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref =getSharedPreferences("refresh",Context.MODE_PRIVATE)
        val editor=pref.edit()
        editor.putBoolean("refresh",false)
        editor.apply()



        if (intent.extras != null) {
            val cityname = intent.getStringExtra("city")
            checkNewCity(cityname!!)
            getDataWithVolley(cityname.toString())
            getTodayDataWithVolley(cityname.toString())
            afterDaysTemp(cityname.toString())
        } else {
            Toast.makeText(this, "empty", Toast.LENGTH_LONG).show()
        }


    }

    @SuppressLint("SetTextI18n")
    private fun getDataWithVolley(cityName: String) {
        val api =
            "https://api.openweathermap.org/data/2.5/weather?q=$cityName&apiKey=91f9810bd3faceeae018e59b663ff3a6"
        val request = JsonObjectRequest(
            Request.Method.GET, api, null,
            {
                val city = it.getString("name")
                val country = it.getJSONObject("sys").getString("country")
                val temp = it.getJSONObject("main").getDouble("temp")
                val status = it.getJSONArray("weather").getJSONObject(0).getString("description")
                val wind = it.getJSONObject("wind").getDouble("speed")
                val percent = it.getJSONObject("main").getInt("humidity")
                val main = it.getJSONArray("weather").getJSONObject(0).getString("main")
                val tempMin = it.getJSONObject("main").getDouble("temp_min")
                val tempMax = it.getJSONObject("main").getDouble("temp_max")


                val cTemp = (temp - 273).toInt()
                val cTempMax = (tempMax - 273).toInt()
                val cTempMin = (tempMin - 273).toInt()


                val tableInfo = TableInfo()
                tableInfo.cityName = city
                tableInfo.countryName = country
                tableInfo.temperature = cTemp
                tableInfo.statuse = main
                tableInfo.windSpeed = wind
                tableInfo.percent = percent
                UserDAO().updateUser(tableInfo)

                binding.txtCityName.text = city
                binding.txtTemp.text = cTemp.toString()
                binding.txtDesc.text = status
                binding.txtTemMin.text = "$cTempMin c"
                binding.txtTempMax.text = "$cTempMax c"
                binding.txtWindSpeed.text = "$wind k/m"
                binding.txtPercent.text = "$percent%"
                checkStatus(main)


            },
            {

            })
        request.retryPolicy = DefaultRetryPolicy(10000, 1, 1f)
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)

        binding.o0.visibility = View.VISIBLE
        binding.o1.visibility = View.VISIBLE
        binding.o2.visibility = View.VISIBLE
        binding.o3.visibility = View.VISIBLE
        binding.o4.visibility = View.VISIBLE
        binding.o5.visibility = View.VISIBLE
        binding.o6.visibility = View.VISIBLE
        binding.txtDesc.visibility = View.VISIBLE
        binding.txtToday.visibility = View.VISIBLE
        binding.cardStatus.visibility = View.VISIBLE
        binding.txtWindSpeed.visibility = View.VISIBLE
        binding.txtTempMax.visibility = View.VISIBLE
        binding.txtTemMin.visibility = View.VISIBLE
        binding.txtPercent.visibility = View.VISIBLE
        binding.cardStatus.visibility = View.VISIBLE

    }


    @SuppressLint("WrongConstant")
    private fun getTodayDataWithVolley(cityName: String) {
        val api =
            "https://api.openweathermap.org/data/2.5/forecast?q=$cityName&appid=91f9810bd3faceeae018e59b663ff3a6"

        val request = JsonObjectRequest(
            Request.Method.GET,
            api, null,
            {

                val threeDesc = it.getJSONArray("list").getJSONObject(0).getJSONArray("weather")
                    .getJSONObject(0).getString("main")
                val sixDesc = it.getJSONArray("list").getJSONObject(1).getJSONArray("weather")
                    .getJSONObject(0).getString("main")
                val nineDesc = it.getJSONArray("list").getJSONObject(2).getJSONArray("weather")
                    .getJSONObject(0).getString("main")
                val twelveDesc = it.getJSONArray("list").getJSONObject(3).getJSONArray("weather")
                    .getJSONObject(0).getString("main")
                val fifteenDesc = it.getJSONArray("list").getJSONObject(4).getJSONArray("weather")
                    .getJSONObject(0).getString("main")
                val eighteenDesc = it.getJSONArray("list").getJSONObject(5).getJSONArray("weather")
                    .getJSONObject(0).getString("main")
                val towneyFirstDesc =
                    it.getJSONArray("list").getJSONObject(6).getJSONArray("weather")
                        .getJSONObject(0).getString("main")
                val towneyFourDesc =
                    it.getJSONArray("list").getJSONObject(7).getJSONArray("weather")
                        .getJSONObject(0).getString("main")


                val temp3 =
                    it.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp")
                val temp6 =
                    it.getJSONArray("list").getJSONObject(1).getJSONObject("main").getDouble("temp")
                val temp9 =
                    it.getJSONArray("list").getJSONObject(2).getJSONObject("main").getDouble("temp")
                val temp12 =
                    it.getJSONArray("list").getJSONObject(3).getJSONObject("main").getDouble("temp")
                val temp15 =
                    it.getJSONArray("list").getJSONObject(4).getJSONObject("main").getDouble("temp")
                val temp18 =
                    it.getJSONArray("list").getJSONObject(5).getJSONObject("main").getDouble("temp")
                val temp21 =
                    it.getJSONArray("list").getJSONObject(6).getJSONObject("main").getDouble("temp")
                val temp24 =
                    it.getJSONArray("list").getJSONObject(7).getJSONObject("main").getDouble("temp")


                val time3 = it.getJSONArray("list").getJSONObject(0).getString("dt_txt")
                val time6 = it.getJSONArray("list").getJSONObject(1).getString("dt_txt")
                val time9 = it.getJSONArray("list").getJSONObject(2).getString("dt_txt")
                val time12 = it.getJSONArray("list").getJSONObject(3).getString("dt_txt")
                val time15 = it.getJSONArray("list").getJSONObject(4).getString("dt_txt")
                val time18 = it.getJSONArray("list").getJSONObject(5).getString("dt_txt")
                val time21 = it.getJSONArray("list").getJSONObject(6).getString("dt_txt")
                val time24 = it.getJSONArray("list").getJSONObject(7).getString("dt_txt")

                val dataShow = arrayListOf<DataShow>(
                    DataShow(threeDesc, temp3, time3),
                    DataShow(sixDesc, temp6, time6),
                    DataShow(nineDesc, temp9, time9),
                    DataShow(twelveDesc, temp12, time12),
                    DataShow(fifteenDesc, temp15, time15),
                    DataShow(eighteenDesc, temp18, time18),
                    DataShow(towneyFirstDesc, temp21, time21),
                    DataShow(towneyFourDesc, temp24, time24)
                )
                binding.recToday.layoutManager =
                    LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
                val adapter = Rec_ShowAdapter(dataShow)
                binding.recToday.adapter = adapter


            },
            {

            }
        )
        request.retryPolicy = DefaultRetryPolicy(10000, 1, 1f)
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)

    }


    @SuppressLint("ResourceType")
    private fun checkStatus(mainStatus: String) {
        when (mainStatus) {
            "Clouds" -> binding.imgStatus.setImageResource(R.drawable.bigcloud)
            "Clear" -> binding.imgStatus.setImageResource(R.drawable.bigsun)
            "Drizzle" -> binding.imgStatus.setImageResource(R.drawable.bigrain)

        }
    }


    private fun afterDaysTemp(cityName: String) {
        val api =
            "https://api.weatherapi.com/v1/forecast.json?key=e5f18be0066446fca5e152514222901&q=$cityName&days=3&aqi=no&alerts=no"

        val request = JsonObjectRequest(
            Request.Method.GET, api, null,
            {
                val d1Max =
                    it.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0)
                        .getJSONObject("day").getDouble("maxtemp_c")
                val d2Max =
                    it.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(1)
                        .getJSONObject("day").getDouble("maxtemp_c")
                val d3Max =
                    it.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(2)
                        .getJSONObject("day").getDouble("maxtemp_c")
                val d1Min =
                    it.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0)
                        .getJSONObject("day").getDouble("mintemp_c")
                val d2Min =
                    it.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(1)
                        .getJSONObject("day").getDouble("mintemp_c")
                val d3Min =
                    it.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(2)
                        .getJSONObject("day").getDouble("mintemp_c")

                val d1Status =
                    it.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0)
                        .getJSONObject("day").getJSONObject("condition").getString("text")
                val d2Status =
                    it.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(1)
                        .getJSONObject("day").getJSONObject("condition").getString("text")
                val d3Status =
                    it.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(2)
                        .getJSONObject("day").getJSONObject("condition").getString("text")
                val afterStatus= arrayOf(d1Status,d2Status,d3Status)

                binding.txtMaxTomorrow.text = d1Max.toInt().toString()
                binding.txtMax2dayAfter.text = d2Max.toInt().toString()
                binding.txtMax3dayAfter.text = d3Max.toInt().toString()
                binding.txtMinTomorrow.text = d1Min.toInt().toString()
                binding.txtMin2dayAfter.text = d2Min.toInt().toString()
                binding.txtMin3dayAfter.text = d3Min.toInt().toString()

                val day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                getDaysAfter(day,afterStatus)


            },
            {

            })
        request.retryPolicy = DefaultRetryPolicy(10000, 1, 1f)
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)
    }


    private fun getDaysAfter(day: Int,status:Array<String>) {
        val days =
            arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        when (day) {
            Calendar.SUNDAY -> {
                binding.txtTomorrow.text = days[1]
                binding.txt2dayAfter.text = days[2]
                binding.txt3dayAfter.text = days[3]
            }
            Calendar.MONDAY -> {
                binding.txtTomorrow.text = days[2]
                binding.txt2dayAfter.text = days[3]
                binding.txt3dayAfter.text = days[4]
            }
            Calendar.TUESDAY -> {
                binding.txtTomorrow.text = days[3]
                binding.txt2dayAfter.text = days[4]
                binding.txt3dayAfter.text = days[5]
            }
            Calendar.WEDNESDAY -> {
                binding.txtTomorrow.text = days[4]
                binding.txt2dayAfter.text = days[5]
                binding.txt3dayAfter.text = days[6]
            }
            Calendar.THURSDAY -> {
                binding.txtTomorrow.text = days[5]
                binding.txt2dayAfter.text = days[6]
                binding.txt3dayAfter.text = days[0]
            }
            Calendar.FRIDAY -> {
                binding.txtTomorrow.text = days[6]
                binding.txt2dayAfter.text = days[0]
                binding.txt3dayAfter.text = days[1]
            }
            Calendar.SATURDAY -> {
                binding.txtTomorrow.text = days[0]
                binding.txt2dayAfter.text = days[1]
                binding.txt3dayAfter.text = days[2]
            }
        }
        for (item in 0..2){
           iconForDayAfter(item,status[item])
        }

    }


    private fun iconForDayAfter(whichDay:Int, status:String){
        when(status){
            "Overcast"->{
                when (whichDay) {
                    0 -> binding.imgTomorrow.setImageResource(R.drawable.cloud)
                    1 -> binding.img2dayAfter.setImageResource(R.drawable.cloud)
                    2 -> binding.img3dayAfter.setImageResource(R.drawable.cloud)
                }

            }
            "Cloudy"->{
                when (whichDay) {
                    0 -> binding.imgTomorrow.setImageResource(R.drawable.clouds)
                    1 -> binding.img2dayAfter.setImageResource(R.drawable.clouds)
                    2 -> binding.img3dayAfter.setImageResource(R.drawable.clouds)
                }
            }
            "Sunny"->{
                when (whichDay) {
                    0 -> binding.imgTomorrow.setImageResource(R.drawable.sun)
                    1 -> binding.img2dayAfter.setImageResource(R.drawable.sun)
                    2 -> binding.img3dayAfter.setImageResource(R.drawable.sun)
                }
            }
            "Partly cloudy"->{
                when (whichDay) {
                    0 -> binding.imgTomorrow.setImageResource(R.drawable.partlycloudy)
                    1 -> binding.img2dayAfter.setImageResource(R.drawable.partlycloudy)
                    2 -> binding.img3dayAfter.setImageResource(R.drawable.partlycloudy)
                }
            }
            "Patchy rain possible"->{
                when (whichDay) {
                    0 -> binding.imgTomorrow.setImageResource(R.drawable.rain)
                    1 -> binding.img2dayAfter.setImageResource(R.drawable.rain)
                    2 -> binding.img3dayAfter.setImageResource(R.drawable.rain)
                }
            }
            "Patchy light drizzle"->{
                when (whichDay) {
                    0 -> binding.imgTomorrow.setImageResource(R.drawable.rain)
                    1 -> binding.img2dayAfter.setImageResource(R.drawable.rain)
                    2 -> binding.img3dayAfter.setImageResource(R.drawable.rain)
                }
            }
            "Light freezing rain"->{
                when (whichDay) {
                    0 -> binding.imgTomorrow.setImageResource(R.drawable.snow)
                    1 -> binding.img2dayAfter.setImageResource(R.drawable.snow)
                    2 -> binding.img3dayAfter.setImageResource(R.drawable.snow)
                }
            }
            "Patchy light snow"->{
                when (whichDay) {
                    0 -> binding.imgTomorrow.setImageResource(R.drawable.snow)
                    1 -> binding.img2dayAfter.setImageResource(R.drawable.snow)
                    2 -> binding.img3dayAfter.setImageResource(R.drawable.snow)
                }
            }
            "Light snow"->{
                when (whichDay) {
                    0 -> binding.imgTomorrow.setImageResource(R.drawable.snow)
                    1 -> binding.img2dayAfter.setImageResource(R.drawable.snow)
                    2 -> binding.img3dayAfter.setImageResource(R.drawable.snow)
                }
            }
            "Patchy moderate snow"->{
                when (whichDay) {
                    0 -> binding.imgTomorrow.setImageResource(R.drawable.snow)
                    1 -> binding.img2dayAfter.setImageResource(R.drawable.snow)
                    2 -> binding.img3dayAfter.setImageResource(R.drawable.snow)
                }
            }
            "Heavy snow"->{
                when (whichDay) {
                    0 -> binding.imgTomorrow.setImageResource(R.drawable.snow)
                    1 -> binding.img2dayAfter.setImageResource(R.drawable.snow)
                    2 -> binding.img3dayAfter.setImageResource(R.drawable.snow)
                }
            }
            "Mist"->{
                when (whichDay) {
                    0 -> binding.imgTomorrow.setImageResource(R.drawable.mist)
                    1 -> binding.img2dayAfter.setImageResource(R.drawable.mist)
                    2 -> binding.img3dayAfter.setImageResource(R.drawable.mist)
                }
            }
            "Heavy rain"->{
                when (whichDay) {
                    0 -> binding.imgTomorrow.setImageResource(R.drawable.rain)
                    1 -> binding.img2dayAfter.setImageResource(R.drawable.rain)
                    2 -> binding.img3dayAfter.setImageResource(R.drawable.rain)
                }
            }
            "Moderate rain"->{
                when (whichDay) {
                    0 -> binding.imgTomorrow.setImageResource(R.drawable.rain)
                    1 -> binding.img2dayAfter.setImageResource(R.drawable.rain)
                    2 -> binding.img3dayAfter.setImageResource(R.drawable.rain)
                }
            }
        }

    }


    @SuppressLint("CommitPrefEdits")
    private fun checkNewCity(cityName: String){
        val pref =getSharedPreferences("refresh",Context.MODE_PRIVATE)
        val editor=pref.edit()
        val result= UserDAO().readAll()
        result.forEach {
            if (it.cityName==cityName){
                editor.putBoolean("refresh",true)
                editor.apply()
            }
        }
    }
}

