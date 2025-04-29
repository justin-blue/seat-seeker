package com.example.data.services

import com.example.data.ApiResponseHandler
import com.example.data.DomainResult
import com.example.data.EventEntity
import com.example.data.Events
import javax.inject.Inject

class GetEventsRepositoryImpl @Inject constructor(
    private val getEventsServiceApi: GetEventsServiceApi,
) : ApiResponseHandler(), GetEventsService {
    override suspend fun getEvents(): DomainResult<Events> {
        return call(getEventsServiceApi.getEventsAsync())
    }

    override suspend fun getEvent(id: String): DomainResult<EventEntity> {
        return call(getEventsServiceApi.getEventAsync(id))
    }
}