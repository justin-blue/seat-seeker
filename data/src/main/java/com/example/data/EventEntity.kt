package com.example.data

import androidx.annotation.Keep

@Keep
data class EventEntity(
    val title: String,
    val url: String,
    val datetime_local: String,
    val datetime_utc: String,
    val score: String,
    val type: String,
    val id: String,
    val short_title: String,
    val performers: List<PerformersEntity>,
    val venue: VenueEntity,
    val stats: StatsEntity
)