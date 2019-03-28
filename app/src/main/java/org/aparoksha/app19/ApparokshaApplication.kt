package org.aparoksha.app19

import android.app.Application
import com.google.firebase.messaging.FirebaseMessaging

class ApparokshaApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseMessaging.getInstance().subscribeToTopic("all")
    }
}