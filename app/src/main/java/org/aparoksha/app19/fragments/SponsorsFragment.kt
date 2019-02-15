package org.aparoksha.app19.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.android.synthetic.main.fragment_sponsors.*
import org.aparoksha.app19.R
import org.aparoksha.app19.adapters.SponsorsAdapter
import org.aparoksha.app19.models.Sponsor
import org.aparoksha.app19.utils.AppDB
import org.jetbrains.anko.toast

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
        db.firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true).build()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sponsors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sponsors_list.layoutManager = LinearLayoutManager(context)
        mSponsorsAdapter = SponsorsAdapter(context!!, sponsorsArrayList)
        sponsors_list.adapter = mSponsorsAdapter

        setupSwipeRefreshLayout()
        if (appDB.getAllSponsors().isEmpty()) {
            sponsors_swipe_refresh.isRefreshing = true
            fetchSponsorsData()
        } else {
            sponsorsArrayList = ArrayList(appDB.getAllSponsors())
            mSponsorsAdapter.updateSponsorsList(sponsorsArrayList)
            sponsors_swipe_refresh.isRefreshing = false
        }
    }

    private fun fetchSponsorsData() {
        db.collection(SPONSORS_COLLECTION)
            .get()
            .addOnSuccessListener {
                sponsorsArrayList.clear()
                for (doc in it.documents) {
                    val sponsor = doc.toObject(Sponsor::class.java)
                    if (sponsor != null) sponsorsArrayList.add(sponsor)
                }
                appDB.storeSponsors(sponsorsArrayList)
                mSponsorsAdapter.updateSponsorsList(sponsorsArrayList)
            }
            .addOnFailureListener {
                context!!.toast("Connection Broke!!")
            }
            .addOnCompleteListener {
                sponsors_swipe_refresh.isRefreshing = false
            }
    }

    private fun setupSwipeRefreshLayout() {
        sponsors_swipe_refresh.setOnRefreshListener {
            fetchSponsorsData()
        }
    }

    companion object {
        const val SPONSORS_COLLECTION = "sponsors"
    }
}