package org.aparoksha.app19.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_team.*
import org.aparoksha.app19.R
import org.aparoksha.app19.adapters.TeamAdapter
import org.aparoksha.app19.models.Person
import org.aparoksha.app19.utils.AppDB

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
        appDb = AppDB.getInstance(context!!)
        db = FirebaseFirestore.getInstance()
        mTeamAdapter = TeamAdapter(context!!, teamArrayList)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        team_list.layoutManager = LinearLayoutManager(context)
        team_list.adapter = mTeamAdapter

        teamArrayList = ArrayList(appDb.getAllTeamMembers())
        mTeamAdapter.updateTeamList(teamArrayList)
    }
}