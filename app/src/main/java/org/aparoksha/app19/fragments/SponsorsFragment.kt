package org.aparoksha.app19.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_sponsors.*
import org.aparoksha.app19.R
import org.aparoksha.app19.adapters.SponsorsAdapter
import org.aparoksha.app19.models.Sponsor
import org.aparoksha.app19.utils.AppDB

class SponsorsFragment : Fragment() {

    private lateinit var mSponsorsAdapter: SponsorsAdapter

    private lateinit var appDB: AppDB
    private lateinit var db: FirebaseFirestore
    private lateinit var sponsorsArrayList: ArrayList<Sponsor>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sponsorsArrayList = ArrayList()
        appDB = AppDB.getInstance(context!!)
        db = FirebaseFirestore.getInstance()
        mSponsorsAdapter = SponsorsAdapter(context!!, sponsorsArrayList)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sponsors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sponsors_list.layoutManager = LinearLayoutManager(context)
        sponsors_list.adapter = mSponsorsAdapter

        sponsorsArrayList = ArrayList(appDB.getAllSponsors())
        mSponsorsAdapter.updateSponsorsList(sponsorsArrayList)
    }
}