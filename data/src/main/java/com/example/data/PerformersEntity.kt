package com.example.data

import androidx.annotation.Keep

@Keep
data class PerformersEntity(
    val type: String,
    val name: String,
    val image: String,
    val id: String
)

data class PerformerType(
    val type: String,
    val name: String,
    val image: String,
    val id: String
)

