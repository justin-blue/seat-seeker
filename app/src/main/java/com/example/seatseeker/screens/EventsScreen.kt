package com.example.seatseeker.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.seatseeker.EventScreenViewModel
import com.example.seatseeker.views.ErrorDialog
import com.example.seatseeker.views.EventsList
import com.example.seatseeker.views.ProgressIndicator

@Composable
fun EventsScreen(
    modifier: Modifier = Modifier,
    eventsViewModel: EventScreenViewModel = hiltViewModel(),
    onEventSelected: (String) -> Unit
) {

    val list by eventsViewModel.eventsFlow.collectAsState()
    val state by eventsViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        eventsViewModel.loadEvents()
    }

    Column(modifier = modifier) {
        when (state) {
            is EventScreenViewModel.State.Loading -> ProgressIndicator()
            is EventScreenViewModel.State.Error -> ErrorDialog(
                onDialogErrorClosed = { eventsViewModel.onErrorDialogClosed() }
            )

            else -> {
                //no-op
            }
        }
        EventsList(
            list = list,
            onEventSelected = { event -> onEventSelected(event) },
            modifier = modifier,
            onRefreshList = { eventsViewModel.refreshList() }
        )
    }

}