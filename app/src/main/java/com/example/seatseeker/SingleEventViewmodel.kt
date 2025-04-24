package com.example.seatseeker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.EventDomain
import com.example.domain.GetSingleEventUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.seatseeker.navigation.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingleEventViewModel @Inject constructor(
    private val getSingleEventUseCase: GetSingleEventUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _eventFlow = MutableStateFlow<EventDomain?>(null)
    val eventsFlow = _eventFlow.asStateFlow()

    private val eventID: String = savedStateHandle[Event.eventID] ?: ""

    private val _state = MutableStateFlow<State?>(null)
    val state = _state.asStateFlow()

    sealed class State {
        object Loading : State()
        object Presenting : State()
        object Error : State()
    }


    fun onErrorDialogClosed() {
        _state.update { State.Presenting }
        loadEvent()
    }


    fun loadEvent() {
        viewModelScope.launch {
            _state.update { State.Loading }
            getSingleEventUseCase(eventID)?.let { event ->
                _eventFlow.update { event }
                _state.update { State.Presenting }
            } ?: _state.update { State.Error }
        }
    }

}