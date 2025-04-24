package com.example.data

import androidx.annotation.Keep

@Keep
data class VenueEntity(
    val state: String,
    val name_v2: String,
    val display_location: String,
    val location: EventLocationEntity,
    val capacity: Int,
    val url: String
)

data class EventLocationEntity(
    val lat: String,
    val lon: String
)