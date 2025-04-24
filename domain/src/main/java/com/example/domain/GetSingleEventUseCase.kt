package com.example.domain

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.DomainResult
import com.example.data.services.GetEventsRepositoryImpl
import javax.inject.Inject

class GetSingleEventUseCase @Inject constructor(
    private val getEventRepository: GetEventsRepositoryImpl,
    private val getEventsMapper: EventEntityToEventDomainMapper
) {
    suspend operator fun invoke(eventID: String): EventDomain? {
        return when (val result = getEventRepository.getEvent(eventID)) {
            is DomainResult.Success -> getEventsMapper.map(listOf(result.data)).first()
            is DomainResult.Error -> null
        }
    }
}