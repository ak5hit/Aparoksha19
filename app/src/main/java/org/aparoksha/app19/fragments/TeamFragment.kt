package org.aparoksha.app19.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.android.synthetic.main.fragment_team.*
import org.aparoksha.app19.R
import org.aparoksha.app19.adapters.TeamAdapter
import org.aparoksha.app19.models.Person
import org.aparoksha.app19.utils.AppDB
import org.jetbrains.anko.toast

class TeamFragment : Fragment() {

    private lateinit var mTeamAdapter: TeamAdapter

    private lateinit var appDb: AppDB
    private lateinit var db: FirebaseFirestore
    private lateinit var teamArrayList: ArrayList<Person>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        teamArrayList = ArrayList()
        mTeamAdapter = TeamAdapter(context!!, teamArrayList)
        appDb = AppDB.getInstance(context!!)
        db = FirebaseFirestore.getInstance()
//        db.firestoreSettings = FirebaseFirestoreSettings.Builder()
//            .setPersistenceEnabled(true).build()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        team_list.layoutManager = LinearLayoutManager(context)
        team_list.adapter = mTeamAdapter

        setupSwipeRefreshLayout()
        if (appDb.getAllTeamMembers().isEmpty()) {
            team_swipe_refresh.isRefreshing = true
            fetchTeamData()
        } else {
            teamArrayList =  ArrayList(appDb.getAllTeamMembers())
            mTeamAdapter.updateTeamList(teamArrayList)
            team_swipe_refresh.isRefreshing = false
        }
    }

    private fun fetchTeamData() {
        db.collection(ORGANISERS_COLLECTION)
            .get()
            .addOnSuccessListener {
                teamArrayList.clear()
                for (doc in it.documents) {
                    val person = doc.toObject(Person::class.java)
                    if (person != null) teamArrayList.add(person)
                }
                appDb.storeTeam(teamArrayList)
                mTeamAdapter.updateTeamList(teamArrayList)
            }
            .addOnFailureListener {
                context!!.toast("Connection Broke!!")
            }
            .addOnCompleteListener {
                team_swipe_refresh.isRefreshing = false
            }
    }

    private fun setupSwipeRefreshLayout() {
        team_swipe_refresh.setOnRefreshListener {
            fetchTeamData()
        }
    }

    companion object {
        const val ORGANISERS_COLLECTION = "organisers"
    }
}