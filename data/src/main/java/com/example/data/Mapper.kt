package com.example.data

interface Mapper<From,To> {
    fun map(from:From):To
}