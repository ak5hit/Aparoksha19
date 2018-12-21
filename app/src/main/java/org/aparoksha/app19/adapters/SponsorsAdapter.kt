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
import org.aparoksha.app19.R
import org.aparoksha.app19.models.Sponsor
import org.jetbrains.anko.browse

class SponsorsAdapter(val context: Context, private var sponsorsList: ArrayList<Sponsor>) :
    RecyclerView.Adapter<SponsorsAdapter.SponsorsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SponsorsViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.sponsors_list_item, parent, false)
        return SponsorsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SponsorsViewHolder, position: Int) {
        holder.bind(sponsorsList[position])
    }

    override fun getItemCount(): Int {
        return sponsorsList.size
    }

    inner class SponsorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val logoIV = itemView.findViewById<ImageView>(R.id.sponsor_logo)
        private val nameTV = itemView.findViewById<TextView>(R.id.sponsor_name)

        fun bind(sponsor: Sponsor) {
            nameTV.text = sponsor.name
            itemView.setOnClickListener {
                context.browse(sponsor.websiteLink, false)
            }
            Glide.with(context)
                .load(sponsor.imageUrl)
                .transition(DrawableTransitionOptions().crossFade())
                .into(logoIV)
        }
    }
}