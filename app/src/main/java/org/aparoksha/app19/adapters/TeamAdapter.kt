package org.aparoksha.app19.adapters

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import org.aparoksha.app19.R
import org.aparoksha.app19.models.Person
import org.jetbrains.anko.makeCall
import org.jetbrains.anko.toast

class TeamAdapter(val context: Context, var teamList: ArrayList<Person>) :
    RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.team_list_item, parent, false)
        return TeamViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(teamList[position])
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    fun updateTeamList(list: ArrayList<Person>) {
        teamList = list
        notifyDataSetChanged()
    }

    inner class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nameTV = itemView.findViewById<TextView>(R.id.organiser_name)
        private val imageIV = itemView.findViewById<ImageView>(R.id.organiser_image)
        private val designationTV = itemView.findViewById<TextView>(R.id.organiser_designation)
        private val callButton = itemView.findViewById<FloatingActionButton>(R.id.organiser_call_button)

        fun bind(person: Person) {
            nameTV.text = person.name
            designationTV.text = person.designation
            callButton.setOnClickListener {
                handleCallPermission()
                context.makeCall(person.phoneNumber.toString())
            }
            Glide.with(context)
                .load(person.imageUrl)
                .transition(DrawableTransitionOptions().crossFade())
                .apply(RequestOptions().circleCrop())
                .into(imageIV)
        }

        private fun handleCallPermission() {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(
                        context as Activity,
                        Manifest.permission.CALL_PHONE
                    )
                ) {
                    context.toast("Grant Call Permission from Settings")
                } else {
                    ActivityCompat.requestPermissions(
                        context,
                        arrayOf(Manifest.permission.CALL_PHONE), 0
                    )
                }
            }
        }
    }
}