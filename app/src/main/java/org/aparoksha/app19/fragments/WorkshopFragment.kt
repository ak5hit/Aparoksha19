package org.aparoksha.app19.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_workshop.*
import org.aparoksha.app19.R
import org.aparoksha.app19.activities.WorkshopDetailsActivity
import org.aparoksha.app19.adapters.WorkshopAdapter
import org.aparoksha.app19.models.Day
import org.aparoksha.app19.models.Workshop
import org.aparoksha.app19.utils.AppDB
import org.jetbrains.anko.toast

class WorkshopFragment : Fragment() {

    private lateinit var appDB: AppDB
    private lateinit var db: FirebaseFirestore

    private lateinit var mWorkshopAdapter: WorkshopAdapter
    private lateinit var mWorkshopList: ArrayList<Workshop>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        db = FirebaseFirestore.getInstance()
        appDB = AppDB.getInstance(context!!)

/*
        for (i in 1..5) {
            val days = ArrayList<Day>()
            for (j in 1..10) {
                val day = Day(
                    j.toString(),
                    j,
                    "Little Description, mostly describing about what are topics that will gonna taught in that workshop.",
                    "CC3",
                    System.currentTimeMillis()
                )
                days.add(day)
            }
            val workshop = Workshop(
                i.toString(),
                "App Development",
                "A workshop concentrating on beginner and for also those who don't even have installed the android studio yet. Do come and RIP iOS.",
                "https://i1.wp.com/thebroodle.com/wp-content/uploads/2017/11/Android-Development-Banner.jpg?resize=696%2C348&ssl=1",
                days
            )
            db.collection(WORKSHOP_COLLECTIONS)
                .add(workshop)
        }
*/

        mWorkshopList = ArrayList()
        mWorkshopAdapter = WorkshopAdapter(context!!, mWorkshopList) { position ->
            val intent = Intent(context, WorkshopDetailsActivity::class.java)
            intent.putExtra("workshopID", mWorkshopList[position].id)
            startActivity(intent)
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workshop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        workshop_list.apply {
            adapter = mWorkshopAdapter
            layoutManager = LinearLayoutManager(context)
        }

        setupSwipeRefreshLayout()
        if (appDB.getAllWorkshops().isEmpty()) {
            workshop_swipe_layout.isRefreshing = true
            fetchWorkshopData()
        } else {
            mWorkshopList = ArrayList(appDB.getAllWorkshops())
            mWorkshopAdapter.updateWorkshopList(mWorkshopList)
            workshop_swipe_layout.isRefreshing = false
        }
    }

    private fun setupSwipeRefreshLayout() {
        workshop_swipe_layout.setOnRefreshListener {
            fetchWorkshopData()
        }
    }

    private fun fetchWorkshopData() {
        db.collection(WORKSHOP_COLLECTIONS)
            .get()
            .addOnSuccessListener {
                mWorkshopList.clear()
                for (doc in it.documents) {
                    val workshop = doc.toObject(Workshop::class.java)
                    if (workshop != null) mWorkshopList.add(workshop)
                }
                appDB.storeWorkshops(mWorkshopList)
                mWorkshopAdapter.updateWorkshopList(mWorkshopList)
            }
            .addOnFailureListener {
                context!!.toast("Connection Broke")
            }
            .addOnCompleteListener {
                workshop_swipe_layout.isRefreshing = false
            }
    }


    companion object {
        private const val APK_MONTH = "apk_month"
        const val WORKSHOP_COLLECTIONS = APK_MONTH + "_workshops"
    }
}
