package com.example.domain


import com.example.data.EventEntity
import com.example.data.Mapper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class EventEntityToEventDomainMapper @Inject constructor() :
    Mapper<List<EventEntity>, List<EventDomain>> {
    private val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy 'at' HH:mm")

    private val inputFormat = DateTimeFormatter.ISO_DATE_TIME
    override fun map(from: List<EventEntity>): List<EventDomain> {
        return from.map { event ->
            with(event) {
                EventDomain(
                    title = title,
                    url = url,
                    score = score,
                    type = type.replace('_',','),
                    id = id,
                    datetimeLocal = convertDateFormat(datetime_local),
                    shortTitle = short_title,
                    stats = StatsDomain(
                        lowestPrice = stats.lowest_price,
                        averagePrice = stats.average_price,
                        highestPrice = stats.highest_price,
                        eventCount = stats.event_count
                    ),
                    venue = VenueDomain(
                        state = venue.state,
                        displayLocation = venue.display_location,
                        nameV2 = venue.name_v2,
                        location = EventLocation(venue.location.lat,venue.location.lon),
                        capacity = venue.capacity.toString(),
                        url = venue.url
                    ),
                    performers = getPerformers(event)
                )
            }
        }
    }

    private fun convertDateFormat(entityDate: String): String {
        val localDate = LocalDateTime.parse(entityDate, inputFormat)
        return localDate.format(dateFormat)
    }

    private fun getPerformers(event: EventEntity): PerformersDomain? {
        event.performers.forEach {
            return PerformersDomain(
                type = it.type,
                id = it.id,
                image = it.image,
                name = it.name
            )
        }
        return null
    }
}