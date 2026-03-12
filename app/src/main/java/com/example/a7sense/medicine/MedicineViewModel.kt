package com.example.a7sense.medicine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MedicineViewModel : ViewModel() {

    private val repository = MedicineRepository()

    private val _medicineList = MutableLiveData<List<medicine_name>>()
    val medicineList: LiveData<List<medicine_name>> = _medicineList

    fun searchMedicines(query: String) {

        repository.searchMedicines(query) { medicines ->
            _medicineList.value = medicines
        }

    }
}