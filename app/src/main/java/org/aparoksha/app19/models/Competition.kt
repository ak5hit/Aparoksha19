package org.aparoksha.app19.models

data class Competition(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var venue: String = "",
    var dateTime: Long = 0
)