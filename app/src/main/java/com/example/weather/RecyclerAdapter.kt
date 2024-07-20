package com.example.weather

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter(val list: ArrayList<MyData>) :
    RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder>() {

    private lateinit var mListener: onItemClickListener
    private lateinit var nListener: OnItemLongListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener=listener

    }


    interface OnItemLongListener {
        fun onItemLongClick(position: Int)
    }

    fun setOnItemLongClickListener(listener: OnItemLongListener) {
        nListener=listener

    }

    inner class CustomViewHolder(itemView: View, listener: onItemClickListener, listener2:OnItemLongListener) : RecyclerView.ViewHolder(itemView) {
        val city_name = itemView.findViewById<TextView>(R.id.cityName)
        val country_name = itemView.findViewById<TextView>(R.id.countryName)
        val temperature = itemView.findViewById<TextView>(R.id.txt_degree)
        val percentOFAir = itemView.findViewById<TextView>(R.id.percentOfAir)
        val wind_speed = itemView.findViewById<TextView>(R.id.windSpeed)
        val status = itemView.findViewById<ImageView>(R.id.img_status)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
            itemView.setOnLongClickListener {
                listener2.onItemLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_recycler, parent, false)
        return CustomViewHolder(view,mListener,nListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.city_name.text = list[position].cityName
        holder.country_name.text = list[position].countryName
        holder.temperature.text = list[position].temperature.toString()
        holder.percentOFAir.text = "${list[position].percent}%"
        holder.wind_speed.text = "${list[position].windSpeed}k/m"


        when (list[position].status) {
            "Clouds" -> holder.status.setImageResource(R.drawable.cloudy)
            "Clear" -> holder.status.setImageResource(R.drawable.suny)
            "Rain" -> holder.status.setImageResource(R.drawable.rainy)
            "Snow" -> holder.status.setImageResource(R.drawable.snowy)
            "Drizzle" -> holder.status.setImageResource(R.drawable.rainy)
            "Thunderstorm" -> holder.status.setImageResource(R.drawable.tornado)
        }
    }

    override fun getItemCount(): Int = list.count()
}