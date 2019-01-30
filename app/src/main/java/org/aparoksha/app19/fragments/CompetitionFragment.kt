package org.aparoksha.app19.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_competition.*
import org.aparoksha.app19.R
import org.aparoksha.app19.adapters.CompetitionsAdapter
import org.aparoksha.app19.models.Competition
import org.aparoksha.app19.utils.AppDB
import org.jetbrains.anko.toast

class CompetitionFragment : Fragment() {

    private lateinit var appDB: AppDB
    private lateinit var db: FirebaseFirestore
    private lateinit var mCompetitionAdapter: CompetitionsAdapter
    private lateinit var mCompetitionsList: ArrayList<Competition>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        appDB = AppDB.getInstance(context!!)
        db = FirebaseFirestore.getInstance()

        mCompetitionsList = ArrayList()
        mCompetitionAdapter = CompetitionsAdapter(context!!, mCompetitionsList)

        /*for (i in 1..10) {
            val competition = Competition(
                i.toString(),
                "Code Red",
                "Code Red is a national level competition, programmers participate in this competition from all over the world. I thing that's enough.",
                "Online",
                System.currentTimeMillis()
            )
            db.collection(COMPETITIONS_COLLECTIONS)
                .add(competition)
        }*/

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_competition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        competitions_list.apply {
            adapter = mCompetitionAdapter
            layoutManager = LinearLayoutManager(context)
        }

        setupSwipeRefreshLayout()
        if (appDB.getAllCompetitions().isEmpty()) {
            competition_swipe_refresh.isRefreshing = true
            fetchCompetitionData()
        } else {
            mCompetitionsList = ArrayList(appDB.getAllCompetitions())
            mCompetitionAdapter.updateCompetitionList(mCompetitionsList)
            competition_swipe_refresh.isRefreshing = false
        }
    }

    private fun fetchCompetitionData() {
        db.collection(COMPETITIONS_COLLECTIONS)
            .get()
            .addOnSuccessListener {
                mCompetitionsList.clear()
                for (doc in it.documents) {
                    val competition = doc.toObject(Competition::class.java)
                    if (competition != null) mCompetitionsList.add(competition)
                }
                appDB.storeCompetitions(mCompetitionsList)
                mCompetitionAdapter.updateCompetitionList(mCompetitionsList)
            }
            .addOnFailureListener {
                context!!.toast("Connection Broke")
            }
            .addOnCompleteListener {
                competition_swipe_refresh.isRefreshing = false
            }
    }

    private fun setupSwipeRefreshLayout() {
        competition_swipe_refresh.setOnRefreshListener {
            fetchCompetitionData()
        }
    }

    companion object {
        private const val APK_MONTH = "apk_month"
        const val COMPETITIONS_COLLECTIONS = APK_MONTH + "_competitions"
    }
}
