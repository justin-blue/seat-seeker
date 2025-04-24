package com.example.domain

import com.example.data.DomainResult
import com.example.data.services.GetEventsRepositoryImpl

import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val getEventsRepositoryImpl: GetEventsRepositoryImpl,
    private val getEventsMapper: EventEntityToEventDomainMapper
) {
    suspend operator fun invoke(): List<EventDomain>? {
        return when (val result = getEventsRepositoryImpl.getEvents()){
            is DomainResult.Success -> getEventsMapper.map(result.data.events)
            is DomainResult.Error -> null
        }
    }
}

