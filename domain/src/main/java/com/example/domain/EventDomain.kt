package com.example.domain

data class EventDomain(
    val title: String,
    val url: String,
    val datetimeLocal: String,
    val score: String,
    val type: String,
    val id: String,
    val shortTitle: String,
    val stats: StatsDomain,
    val venue: VenueDomain,
    val performers: PerformersDomain?
)