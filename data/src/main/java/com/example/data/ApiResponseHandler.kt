package com.reallyhandyapps.data

import com.example.data.DomainResult
import kotlinx.coroutines.Deferred
import retrofit2.Response

abstract class ApiResponseHandler {
    suspend fun <T> call(method: Deferred<T>): DomainResult<T> {
        try {
            val response = method.await()
            return if (response is Response<*>) {
                return if (response.isSuccessful) {
                    DomainResult.Success(response)
                } else {
                    DomainResult.Error(Throwable())
                }
            } else {
                DomainResult.Success(response)
            }
        } catch (error: Throwable) {
            return DomainResult.Error(Throwable("Network error"))
        }
    }
}