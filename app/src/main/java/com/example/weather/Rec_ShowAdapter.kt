package com.example.weather

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Rec_ShowAdapter(private val data: ArrayList<DataShow>) :
    RecyclerView.Adapter<Rec_ShowAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val temp = itemView.findViewById<TextView>(R.id.txt_temp)
        val status = itemView.findViewById<ImageView>(R.id.img_status)
        val time = itemView.findViewById<TextView>(R.id.txt_time)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_show, parent, false)
        return CustomViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val time: String
        time = if (data[position].time[11] == '0') {
            data[position].time[12].toString()
        } else {
            "${data[position].time[11]}${data[position].time[12]}"
        }

        if (data[position].desc == "Clouds") {
            holder.status.setImageResource(R.drawable.cloudy)
        } else if (data[position].desc == "Clear") {
            if (time.toInt() in 6..18)holder.status.setImageResource(R.drawable.suny)
            else holder.status.setImageResource(R.drawable.moony)
        }

        when {
            time.toInt() > 12 -> holder.time.text = "${time.toInt() - 12} PM"
            time.toInt() == 12 -> holder.time.text = "12 PM"
            else -> holder.time.text = "$time AM"
        }

        holder.temp.text = (data[position].temp.toInt() - 273).toString()


    }

    override fun getItemCount(): Int = data.count()
}