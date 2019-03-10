package org.aparoksha.app19.viewmodels

import android.app.Application
import android.arch.lifecycle.*
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.aparoksha.app19.models.Event
import org.aparoksha.app19.utils.AppDB

class EventsViewModel(application : Application) : AndroidViewModel(application) {
    private val TAG = EventsViewModel::class.java.simpleName

    val allEvents = MutableLiveData<ArrayList<Event>>()

    fun getData(isFetchNeeded : Boolean) {
        val dataBase = AppDB.getInstance(getApplication())
        val data = ArrayList(dataBase.getAllEvents())
        allEvents.postValue(data)
        if(isFetchNeeded) {
            GlobalScope.launch {
                val ref = FirebaseDatabase.getInstance().reference.child("events")
                ref.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        //some error
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        Log.d(TAG, snapshot.children.toList().size.toString())
                        val list = ArrayList<Event>()
                        for (snap in snapshot.children) {
                            Log.d(TAG, snap.toString())
                            val currVal = snap.getValue(Event::class.java)
                            list.add(currVal!!)
                            Log.d(TAG, "${currVal.id} => ${currVal.name}" )
                        }
                        dataBase.storeEvents(list)
                        allEvents.postValue(list)
                    }
                })
            }
        }

    }
}