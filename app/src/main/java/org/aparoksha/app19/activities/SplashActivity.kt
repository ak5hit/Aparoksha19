package org.aparoksha.app19.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.google.firebase.firestore.FirebaseFirestore
import org.aparoksha.app19.R
import org.aparoksha.app19.models.Event
import org.aparoksha.app19.models.Person
import org.aparoksha.app19.models.Sponsor
import org.aparoksha.app19.utils.AppDB
import org.aparoksha.app19.viewmodels.EventsViewModel
import org.jetbrains.anko.toast

class SplashActivity : AppCompatActivity() {

    private lateinit var appDb: AppDB
    private lateinit var db: FirebaseFirestore
    private lateinit var teamArrayList: ArrayList<Person>
    private lateinit var sponsorsArrayList: ArrayList<Sponsor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)

        teamArrayList = ArrayList()
        sponsorsArrayList = ArrayList()
        appDb = AppDB.getInstance(this)
        db = FirebaseFirestore.getInstance()

        fetchAndStoreData()
    }

    private fun fetchAndStoreData() {
        db.collection(ORGANISERS_COLLECTION)
                .get()
                .addOnSuccessListener { organisers ->
                    teamArrayList.clear()
                    for (doc in organisers.documents) {
                        val person = doc.toObject(Person::class.java)
                        if (person != null) teamArrayList.add(person)
                    }
                    appDb.storeTeam(teamArrayList)

                    db.collection(SPONSORS_COLLECTION)
                            .get()
                            .addOnSuccessListener { sponsors ->
                                sponsorsArrayList.clear()
                                for (doc in sponsors.documents) {
                                    val sponsor = doc.toObject(Sponsor::class.java)
                                    if (sponsor != null) sponsorsArrayList.add(sponsor)
                                }
                                appDb.storeSponsors(sponsorsArrayList)

                                val allEvents = ViewModelProviders.of(this).get(EventsViewModel::class.java)
                                allEvents.getData(true)
                                allEvents.allEvents.observe(this, Observer {
                                    it?.let {
                                        if(it.isNotEmpty()) {
                                            startActivity(Intent(this, MainActivity::class.java))
                                            finish()
                                        }

                                    }
                                })
                            }
                            .addOnFailureListener {
                                toast("Check internet connection.")
                            }
                }
                .addOnFailureListener {
                    toast("Check internet connection.")
                }
    }

    companion object {
        const val ORGANISERS_COLLECTION = "organisers"
        const val SPONSORS_COLLECTION = "sponsors"
    }
}
