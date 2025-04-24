package com.example.domain

data class VenueDomain(
    val state: String? = "",
    val nameV2: String? = "",
    val displayLocation: String? = "",
    val location: EventLocation?,
    val capacity: String,
    val url: String
)

data class EventLocation(
    val lat: String,
    val lon: String
)