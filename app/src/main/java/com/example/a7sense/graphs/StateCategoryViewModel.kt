package com.example.a7sense.graphs

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StateCategoryViewModel : ViewModel() {

    private val repository = StateRepository()

    private val _states =
        MutableStateFlow<List<String>>(emptyList())
    val states: StateFlow<List<String>> = _states



    private val _stateData =
        MutableStateFlow<Map<String, Long>>(emptyMap())
    val stateData: StateFlow<Map<String, Long>> = _stateData

    private fun fetchStates() {
        repository.getStates { result ->
            _states.value = result
        }
    }

    init {
        fetchStates()
    }


    fun fetchState(state: String) {
        repository.getStateData(state) { result ->
            _stateData.value = result
        }
    }
}