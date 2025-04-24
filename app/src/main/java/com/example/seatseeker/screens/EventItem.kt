package com.example.seatseeker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.domain.EventDomain


@Composable
fun EventItem(
    event: EventDomain,
    onEventSelected: (eventId: String) -> Unit
) {
    Box(
        Modifier
            .padding(16.dp)
            .fillMaxSize()
            .clickable { onEventSelected(event.id) }
            .border(1.dp, Color.Gray)
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            elevation = CardDefaults.cardElevation()
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = event.performers?.image,
                contentDescription = "image",
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(8.dp)
                .align(
                    Alignment.BottomStart
                )
        ) {

            Text(text = event.shortTitle, modifier = Modifier.padding(bottom = 8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(text = event.datetimeLocal)
                event.venue.displayLocation?.let { Text(text = it) }
            }
        }
    }
}