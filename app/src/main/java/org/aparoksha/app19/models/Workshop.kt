package org.aparoksha.app19.models

data class Workshop(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var bgImageUrl: String = "",
    var days: ArrayList<Day> = ArrayList()
)

data class Day(
    var id: String = "",
    var number: Int = -1,
    var description: String = "",
    var venue: String = "",
    var dateTime: Long = 0
)
