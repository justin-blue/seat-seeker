package com.example.data

import androidx.annotation.Keep

@Keep
data class StatsEntity (
        val event_count: Int,
        val average_price: Int,
        val lowest_price: Int,
        val highest_price: Int,
        )