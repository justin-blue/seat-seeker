package com.example.seatseeker

import app.cash.turbine.test
import com.example.domain.EventDomain
import com.example.domain.GetEventsUseCase
import io.mockk.mockk
import org.junit.Test
import kotlinx.coroutines.test.setMain

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)
class EventScreenViewModelTest {
    private val getEventsUseCase: GetEventsUseCase = mockk(relaxed = true)
    private val viewModel = EventScreenViewModel(getEventsUseCase)



    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `Given refresh list is called Then list is set to empty and then events are loaded`() =
        runTest {
            //Given
            val mockEventDomain: EventDomain = mockk(relaxed = true)
            coEvery { getEventsUseCase() } returns listOf(mockEventDomain)
            //When
            viewModel.refreshList()

            //Then
            viewModel.eventsFlow.test {
                assertThat(awaitItem()).isEqualTo(emptyList<EventDomain>())
                assertThat(awaitItem()).isEqualTo(listOf(mockEventDomain))
                advanceUntilIdle()
            }
            coVerify { getEventsUseCase() }
            viewModel.state.test {
                assertThat(awaitItem()).isEqualTo(EventScreenViewModel.State.Presenting)
                advanceUntilIdle()
            }
        }

    @Test
    fun `Given events are loaded successfully Then state is set to presenting`() = runTest {
        //Given
        val mockEventDomain: EventDomain = mockk(relaxed = true)
        coEvery { getEventsUseCase() } returns listOf(mockEventDomain)
        //When
        viewModel.loadEvents()
        //Then
        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(EventScreenViewModel.State.Presenting)
            advanceUntilIdle()
        }
    }

    @Test
    fun `Given events are not loaded successfully Then state is set to error`() = runTest {
        //Given
        coEvery { getEventsUseCase() } returns null
        //When
        viewModel.loadEvents()
        //Then
        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(EventScreenViewModel.State.Error)
            advanceUntilIdle()

        }
    }

    @Test
    fun `Given error dialog is closed Then events are loaded`() = runTest {
        //Given
        val mockEventDomain: EventDomain = mockk(relaxed = true)
        coEvery { getEventsUseCase() } returns listOf(mockEventDomain)
        //When
        viewModel.onErrorDialogClosed()
        //Then
        viewModel.eventsFlow.test {
            assertThat(awaitItem()).isEqualTo(listOf(mockEventDomain))
            advanceUntilIdle()
        }
    }
}