package org.aparoksha.app19.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.notification_container.view.*
import org.aparoksha.app19.R
import org.aparoksha.app19.models.Notification
import kotlin.collections.ArrayList


class NotificationAdapter(private val noNotifsTV : TextView)
    : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {


    private val notificationList = ArrayList<Notification>()
    override fun getItemCount(): Int = notificationList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.notification_container, parent, false))
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bindView(notificationList.get(position))
    }

    class NotificationViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

        fun bindView(notification: Notification) {
            itemView.titleTV.text = notification.title
            itemView.descriptionTV.text = notification.description
        }
    }

    fun swapList(list: ArrayList<Notification>){
        notificationList.clear()
        notificationList.addAll(list)
        noNotifsTV.visibility = when(list.size){
            0-> View.VISIBLE
            else -> View.GONE
        }
        notifyDataSetChanged()
    }

    fun add(notification: Notification) {
        notificationList.add(0, notification)
        noNotifsTV.visibility = View.GONE
        notifyItemInserted(0)
    }
}