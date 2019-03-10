package org.aparoksha.app19.adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.event_container.view.*
import org.aparoksha.app19.R
import org.aparoksha.app19.activities.EventsDetailActivity
import org.aparoksha.app19.models.Event
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*


class EventsAdapter : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    private var eventsList: List<Event> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.event_container,parent,false))

    override fun getItemCount(): Int = eventsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(eventsList[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(event: Event) {

            with(itemView) {
                eventNameTV.text = event.name

                val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/India"))
                if (event.timestamp < 100L) {
                    eventTimeTV.text = "Online Event"
                } else {
                    calendar.timeInMillis = event.timestamp.times(1000L)

                    val sdf = SimpleDateFormat("MMM d, hh:mm a")
                    sdf.timeZone = TimeZone.getTimeZone("Asia/India")
                    eventTimeTV.text = sdf.format(calendar.time)
                }

                Glide.with(this).load(event.imageUrl).into(eventImage)

                setOnClickListener {
                    val intent = Intent(context, EventsDetailActivity::class.java)
                    val optionsCompat = ActivityOptions.makeSceneTransitionAnimation(context as Activity)
                    intent.putExtra("id", event.id)
                    startActivity(context, intent, optionsCompat.toBundle())
                }
            }
        }
    }

    fun updateEvents(events: List<Event>) {
        eventsList = events
        notifyDataSetChanged()
    }
}