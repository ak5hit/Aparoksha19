package org.aparoksha.app19.models

data class Notification(
        val description: String = "",
        val eventId : String = "",
        val senderName : String = "",
        val timestamp : Long = 0,
        val title : String = "",
        val verified : Boolean = false
)