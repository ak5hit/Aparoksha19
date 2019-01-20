package org.aparoksha.app19.viewmodels

import android.arch.lifecycle.*
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.aparoksha.app19.models.Event

class EventsViewModel : ViewModel() {
    private val TAG = EventsViewModel::class.java.simpleName

    val allEvents = MutableLiveData<ArrayList<Event>>()

    init {

            getData()
    }

    private fun showData() {
        allEvents.value?.let {
            for(element in it)
                Log.d(TAG, element.name)
        }
    }

    private fun getData() {
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
                    showData()
                }
            }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }
        }
    }
}