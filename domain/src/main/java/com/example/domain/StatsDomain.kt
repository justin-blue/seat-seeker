package com.example.domain

data class StatsDomain(
    val eventCount: Int? = 0,
    val averagePrice: Int,
    val lowestPrice: Int,
    val highestPrice: Int,
)