package com.example.data

import androidx.annotation.Keep
import com.example.data.EventEntity

@Keep
data class Events(
    val events: List<EventEntity>
)