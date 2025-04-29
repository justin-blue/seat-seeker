package com.example.domain

import com.example.data.DomainResult
import com.example.data.EventEntity
import com.example.data.Events
import com.example.data.services.GetEventsRepositoryImpl
import com.example.data.services.GetEventsServiceApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Deferred
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest


import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetEventsRepositoryImplTest {
    private val getEventsServiceApi: GetEventsServiceApi = mockk(relaxed = true)
    private val getEventsRepository = GetEventsRepositoryImpl(getEventsServiceApi)


    @Test
    fun `Given events are returned successfully Then use case returns events`() = runTest {
        val events: Events = mockk(relaxed = true)
        val response: Deferred<Events> = mockk(relaxed = true) {
            coEvery { await() } returns events
        }
        coEvery { getEventsServiceApi.getEventsAsync() } returns response
        //When
        val result = getEventsRepository.getEvents()
        advanceUntilIdle()
        //Then
        assertThat(result).isEqualTo(DomainResult.Success(events))

    }

    @Test
    fun `Given event is returned successfully Then use case returns event`() = runTest {
        //Given
        val event: EventEntity = mockk(relaxed = true)
        val response: Deferred<EventEntity> = mockk(relaxed = true) {
            coEvery { await() } returns event
        }
        coEvery { getEventsServiceApi.getEventAsync("eventId") } returns response
        //When
        val result = getEventsRepository.getEvent("eventId")
        advanceUntilIdle()
        //Then
        assertThat(result).isEqualTo(DomainResult.Success(event))
    }
}

