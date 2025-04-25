package com.example.domain

import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.example.data.EventEntity
import com.example.data.EventLocationEntity
import com.example.data.PerformersEntity
import com.example.data.StatsEntity
import com.example.data.VenueEntity


class EventEntityToEventDomainMapperTest {
    private val mapper: EventEntityToEventDomainMapper = EventEntityToEventDomainMapper()

    @Test
    fun `Given a list of events, when mapped, then a list of domain events is returned`() {
        //Given
        val firstEvent = EventEntity(
            "number one event",
            "http://fakeurl.com",
            "2023-03-23T07:30:00",
            "26",
            "345",
            "concert_festival_dance",
            "someID",
            "first event",
            listOf(PerformersEntity("singer", "joe bloggs", "image.url", "performID")),
            VenueEntity(
                "GA",
                "Lucas theatre",
                "Savannah,GA",
                EventLocationEntity("32.0788", "-81.0893"),
                23500,
                "venueurl.com"
            ),
            StatsEntity(56, 23, 16, 44)
        )

        val secondEvent = EventEntity(
            "number two event",
            "http://anotherfakeurl.com",
            "2023-04-23T07:30:00",
            "2023-04-23T07:30:00",
            "345",
            "entertainment_comedy_performance",
            "anotherID",
            "second event",
            listOf(PerformersEntity("performer", "judy dee", "image.url", "performanceID")),
            VenueEntity(
                "FL",
                "Music park",
                "Savannah,GA",
                EventLocationEntity("30.3972", "-82.9466"),
                23500,
                "floridavenueurl.com"
            ),
            StatsEntity(23, 44, 22, 106)
        )
        //When
        val result = mapper.map(listOf(firstEvent, secondEvent))

        //Then
        with(result) {
            assertThat(size).isEqualTo(2)
            assertThat(get(0).type).isEqualTo("concert,festival,dance")
            assertThat(get(0).datetimeLocal).isEqualTo("23/03/2023 at 07:30")
            assertThat(get(0).performers?.name).isEqualTo("joe bloggs")
            assertThat(get(0).venue.location?.lat).isEqualTo("32.0788")
            assertThat(get(0).venue.state).isEqualTo("GA")
            assertThat(get(0).stats.lowestPrice).isEqualTo(16)
            assertThat(get(1).type).isEqualTo("entertainment,comedy,performance")
            assertThat(get(1).datetimeLocal).isEqualTo("23/04/2023 at 07:30")
            assertThat(get(1).performers?.name).isEqualTo("judy dee")
            assertThat(get(1).venue.location?.lon).isEqualTo("-82.9466")
            assertThat(get(1).venue.state).isEqualTo("FL")
            assertThat(get(1).venue.nameV2).isEqualTo("Music park")
            assertThat(get(1).stats.eventCount).isEqualTo(23)
        }

    }

}