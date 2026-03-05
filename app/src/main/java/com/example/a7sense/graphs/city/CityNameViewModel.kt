package com.example.a7sense.graphs.city

import androidx.lifecycle.ViewModel
import com.example.a7sense.graphs.StateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CityNameViewModel: ViewModel() {

    private val repository = CityVsNameRepository()

    private val _cities =
        MutableStateFlow<List<String>>(emptyList())
    val cities: StateFlow<List<String>> = _cities



    private val _cityData =
        MutableStateFlow<Map<String, Long>>(emptyMap())
    val cityData: StateFlow<Map<String, Long>> = _cityData

    private fun fetchCities() {
        repository.getCityNames { result ->
            _cities.value = result
        }
    }

    init {
        fetchCities()
    }


    fun GetCityData(state: String) {
        repository.getCityData(state) { result ->
            _cityData.value = result
        }
    }
}