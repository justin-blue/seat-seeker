package com.example.seatseeker.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.domain.EventDomain
import com.example.seatseeker.screens.EventItem

@Composable
fun EventsList(
    list: List<EventDomain>,
    onEventSelected: (event: String) -> Unit,
    onRefreshList: () -> Unit,
    modifier: Modifier
) {
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.SpaceEvenly,
            content = {
                items(list) { event ->
                    EventItem(event = event, onEventSelected = { onEventSelected(event.id) })
                }
                item{
                    Button(
                        onClick = { onRefreshList() },
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth()
                    ) {
                        Text("Refresh")
                    }
                }
            },
        )
}