package org.aparoksha.app19.viewmodels

import android.app.Application
import android.arch.lifecycle.*
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.aparoksha.app19.models.Event
import org.aparoksha.app19.utils.AppDB

class EventsViewModel(application : Application) : AndroidViewModel(application) {
    private val TAG = EventsViewModel::class.java.simpleName

    val allEvents = MutableLiveData<ArrayList<Event>>()

    init {
        getData(true)
    }

    private fun showData() {
        allEvents.value?.let {
            for(element in it)
                Log.d(TAG, element.name)
        }
    }

    private fun getData(isFetchNeeded : Boolean) {
        val dataBase = AppDB.getInstance(getApplication())
        val data = ArrayList(dataBase.getAllEvents())
        allEvents.postValue(data)
        if(isFetchNeeded) {
            GlobalScope.launch {
                FirebaseFirestore.getInstance().collection("events").get().addOnSuccessListener {
                    val list = ArrayList<Event>()
                    it?.let {
                        for (doc in it) {
                            val data = doc.toObject(Event::class.java)
                            list.add(data)
                            Log.d(TAG, doc.id + " => " + doc.data)
                        }
                        allEvents.postValue(list)
                        dataBase.storeEvents(list)
                        showData()
                    }
                }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "Error getting documents: ", exception)
                    }
            }
        }

    }
}