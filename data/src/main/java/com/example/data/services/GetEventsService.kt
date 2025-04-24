package com.example.data.services

import com.example.data.DomainResult
import com.example.data.EventEntity
import com.example.data.Events

interface GetEventsService {
    suspend fun getEvents(): DomainResult<Events>
    suspend fun getEvent(id: String): DomainResult<EventEntity>
}