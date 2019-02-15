package org.aparoksha.app19.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.aparoksha.app19.R
import org.aparoksha.app19.models.Day
import java.text.SimpleDateFormat
import java.util.*

class WorkshopDayAdapter(mContext: Context, private var mDays: ArrayList<Day>) :
    RecyclerView.Adapter<WorkshopDayAdapter.WorkshopDayHolder>() {

    private val layoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, itemType: Int): WorkshopDayHolder {
        return WorkshopDayHolder(layoutInflater.inflate(R.layout.item_workshop_day, parent, false))
    }

    override fun onBindViewHolder(holder: WorkshopDayHolder, position: Int) {
        holder.bind(mDays[position])
    }

    override fun getItemCount(): Int = mDays.size

    inner class WorkshopDayHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(day: Day) {
            itemView.findViewById<TextView>(R.id.dayTV).text = "Day ${day.number}"
            itemView.findViewById<TextView>(R.id.descriptionTV).text = day.description
            itemView.findViewById<TextView>(R.id.venueTV).text = day.venue
            val sdf = SimpleDateFormat("HH:mm|dd/MM", Locale.ENGLISH)
            itemView.findViewById<TextView>(R.id.timeTV).text = sdf.format(day.dateTime)
        }
    }
}