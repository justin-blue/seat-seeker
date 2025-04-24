package com.example.domain
import com.example.data.DomainResult
import com.example.data.Events
import com.example.data.services.GetEventsRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
class GetEventsUseCaseTest {
    private val getEventsRepo: GetEventsRepositoryImpl = mockk(relaxed = true)
    private val getEventsMapper: EventEntityToEventDomainMapper = mockk(relaxed = true)
    private val getEventsUseCase = GetEventsUseCase(getEventsRepo, getEventsMapper)

    @Test
    fun `Given events are returned successfully Then use case returns events`(): Unit =
        runBlocking {
            //Given
            val mockEventDomain: EventDomain = mockk(relaxed = true) {}

            val mockEvents: Events = mockk(relaxed = true)
            coEvery { getEventsMapper.map(any()) } returns listOf(mockEventDomain)
            coEvery { getEventsRepo.getEvents() } returns DomainResult.Success(mockEvents)

            //When
            val result = getEventsUseCase()

            //Then
            assertThat(result).isEqualTo(listOf(mockEventDomain))

        }

    @Test
    fun `Given events are not returned successfully Then use case returns null`(): Unit =
        runBlocking {
            //Given

            coEvery { getEventsRepo.getEvents() } returns DomainResult.Error(Exception())
            //When
            val result = getEventsUseCase()
            //Then
            coVerify(inverse = true) { getEventsMapper.map(any()) }
            assertThat(result).isNull()

        }
}