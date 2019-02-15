package org.aparoksha.app19.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import org.aparoksha.app19.R
import org.aparoksha.app19.models.Workshop

class WorkshopAdapter(
    private val mContext: Context,
    private var mWorkshopsList: ArrayList<Workshop>,
    private val onItemClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<WorkshopAdapter.WorkshopHolder>() {

    private val layoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkshopHolder {
        val view = layoutInflater.inflate(R.layout.workshop_list_item, parent, false)
        return WorkshopHolder(view)
    }

    override fun onBindViewHolder(holder: WorkshopHolder, position: Int) {
        holder.bind(mWorkshopsList[position])
        holder.itemView.setOnClickListener { onItemClick(position) }
    }

    override fun getItemCount(): Int = mWorkshopsList.size

    fun updateWorkshopList(list: ArrayList<Workshop>) {
        mWorkshopsList = list
        notifyDataSetChanged()
    }

    inner class WorkshopHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.findViewById<TextView>(R.id.titleTV)
        private val description = itemView.findViewById<TextView>(R.id.descriptionTV)
        private val bg = itemView.findViewById<ImageView>(R.id.workshop_bg)

        fun bind(workshop: Workshop) {
            title.text = workshop.title
            description.text = workshop.description
            Glide.with(mContext)
                .load(workshop.bgImageUrl)
                .transition(DrawableTransitionOptions().crossFade())
                .into(bg)
        }
    }
}