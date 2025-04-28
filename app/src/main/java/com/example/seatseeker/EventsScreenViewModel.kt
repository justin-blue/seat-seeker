package com.example.seatseeker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.EventDomain
import com.example.domain.GetEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventScreenViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase
) : ViewModel() {
    private val _eventsFlow = MutableStateFlow<List<EventDomain>>(emptyList())
    val eventsFlow = _eventsFlow.asStateFlow()


    private val _state = MutableStateFlow<State?>(null)
    val state = _state.asStateFlow()

    sealed class State {
       data object Loading : State()
       data object Presenting : State()
       data object Error : State()
    }

    fun onErrorDialogClosed() {
        _state.update { State.Presenting }
        loadEvents()
    }


    fun refreshList() {
        _eventsFlow.update { emptyList() }
        loadEvents()
    }


    fun loadEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { State.Loading }
            val response = getEventsUseCase()
            if (response != null) {
                _eventsFlow.update { response }
                _state.update { State.Presenting }
            } else {
                _state.update { State.Error }
            }
        }
    }


}