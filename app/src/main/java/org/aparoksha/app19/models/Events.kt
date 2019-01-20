package org.aparoksha.app19.models

data class Event(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val location: String = "",
    val timestamp: Long = 0,
    val duration: Long = 0,
    val imageUrl: String = "",
    val categories: List<String> = ArrayList(listOf("Test")),
    val additionalInfo: List<String> = emptyList(),
    val facebookEventLink: String = "",
    val organizers: List<Organizer> = emptyList()
)

data class Organizer(
    val name: String = "",
    val phoneNumber: Long = 0
)