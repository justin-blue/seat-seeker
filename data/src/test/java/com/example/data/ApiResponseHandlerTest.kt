package com.example.data

import io.mockk.coEvery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import io.mockk.mockk
import kotlinx.coroutines.Deferred
import com.google.common.truth.Truth.assertThat

@OptIn(ExperimentalCoroutinesApi::class)
class ApiResponseHandlerTest {
    private val fakeApiResponseHandler = FakeApiResponseHandler()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `call returns success when response is successful`() = runTest {
        //Given
        val mockResponse: Response<String> = mockk(relaxed = true) {
            coEvery { isSuccessful } returns true
        }
        val mockDeferredResponse: Deferred<Response<String>> = mockk(relaxed = true) {
            coEvery { await() } returns mockResponse

        }
        //When
        val response = fakeApiResponseHandler.call(mockDeferredResponse)
        //Then
        assertThat(response).isEqualTo(DomainResult.Success(mockResponse))
    }

    @Test
    fun `call returns error when response is not successful`()= runTest {
        //Given
        val mockResponse: Response<String> = mockk(relaxed = true) {
            coEvery { isSuccessful } returns false
        }
        val mockDeferredResponse: Deferred<Response<String>> = mockk(relaxed = true) {
            coEvery { await() } returns mockResponse

        }
        //When
        val response = fakeApiResponseHandler.call(mockDeferredResponse)
        //Then
        assertThat(response).isInstanceOf(DomainResult.Error::class.java)
    }
}