package org.aparoksha.app19.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.aparoksha.app19.R
import org.aparoksha.app19.models.Competition
import java.text.SimpleDateFormat
import java.util.*

class CompetitionsAdapter(private val mContext: Context, var mCompetitionsList: ArrayList<Competition>) :
    RecyclerView.Adapter<CompetitionsAdapter.CompetitionViewHolder>() {

    private val layoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetitionViewHolder {
        val view = layoutInflater.inflate(R.layout.competition_list_item, parent, false)
        return CompetitionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompetitionViewHolder, position: Int) {
        holder.bind(mCompetitionsList[position])
    }

    override fun getItemCount(): Int {
        return mCompetitionsList.size
    }

    public fun updateCompetitionList(list: ArrayList<Competition>) {
        mCompetitionsList = list
        notifyDataSetChanged()
    }

    inner class CompetitionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mTitle = itemView.findViewById<TextView>(R.id.titleTV)
        private val mDescription = itemView.findViewById<TextView>(R.id.descriptionTV)
        private val mVenue = itemView.findViewById<TextView>(R.id.venueTV)
        private val mTime = itemView.findViewById<TextView>(R.id.timeTV)

        fun bind(competition: Competition) {
            mTitle.text = competition.title
            mDescription.text = competition.description
            mVenue.text = competition.venue
            val sdf = SimpleDateFormat("HH:mm|dd/MM", Locale.ENGLISH)
            mTime.text = sdf.format(Date(competition.dateTime))
        }

    }
}