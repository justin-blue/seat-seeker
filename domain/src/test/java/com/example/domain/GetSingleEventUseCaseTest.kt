package com.example.domain

import com.example.data.DomainResult
import com.example.data.EventEntity
import com.example.data.services.GetEventsRepositoryImpl
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test


class GetSingleEventUseCaseTest{
  private val getEventsRepo: GetEventsRepositoryImpl = mockk(relaxed = true)
  private val getEventsMapper: EventEntityToEventDomainMapper = mockk(relaxed = true)
  private val getEventsUseCase = GetSingleEventUseCase(getEventsRepo, getEventsMapper)

  @Test
  fun `Given events are returned successfully Then use case returns events`(): Unit =
   runBlocking {
    //Given
    val mockEventDomain: EventDomain = mockk(relaxed = true) {}

    val mockEvent: EventEntity = mockk(relaxed = true)
    coEvery { getEventsMapper.map(any()) } returns listOf(mockEventDomain)
    coEvery { getEventsRepo.getEvent(any()) } returns DomainResult.Success(mockEvent)

    //When
    val result = getEventsUseCase("eventId")

    //Then
    assertThat(result).isEqualTo(mockEventDomain)

   }

  @Test
  fun `Given events are not returned successfully Then use case returns null`(): Unit =
   runBlocking {
    //Given

    coEvery { getEventsRepo.getEvents() } returns DomainResult.Error(Exception())
    //When
    val result = getEventsUseCase("id")
    //Then
    coVerify(inverse = true) { getEventsMapper.map(any()) }
    assertThat(result).isNull()

   }
 }