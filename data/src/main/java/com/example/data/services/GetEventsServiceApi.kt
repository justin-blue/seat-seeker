package com.example.data.services
import com.example.data.EventEntity
import com.example.data.Events
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface GetEventsServiceApi {

    @GET("events?client_id=MzI1MDM0Mzl8MTY3OTMwODM2Ny45NzU3OTg0")
    fun getEventsAsync(): Deferred<Events>

    @GET("events/{EVENT_ID}?client_id=MzI1MDM0Mzl8MTY3OTMwODM2Ny45NzU3OTg0")
    fun getEventAsync(@Path("EVENT_ID") eventId: String): Deferred<EventEntity>
}