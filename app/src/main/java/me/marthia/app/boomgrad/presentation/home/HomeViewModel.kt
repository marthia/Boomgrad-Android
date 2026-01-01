package me.marthia.app.boomgrad.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.marthia.app.boomgrad.domain.model.City
import me.marthia.app.boomgrad.domain.model.County
import me.marthia.app.boomgrad.domain.model.Province
import me.marthia.app.boomgrad.domain.usecase.common.GetCityUseCase
import me.marthia.app.boomgrad.domain.usecase.common.GetCountyUseCase
import me.marthia.app.boomgrad.domain.usecase.common.GetProvinceUseCase

class HomeViewModel(
    private val getCity: GetCityUseCase,
    private val getCounty: GetCountyUseCase,
    private val getProvince: GetProvinceUseCase,
) : ViewModel() {

    private val _provinces = MutableStateFlow<List<Province>>(emptyList())
    val provinces: StateFlow<List<Province>> = _provinces.asStateFlow()

    private val _counties = MutableStateFlow<List<County>>(emptyList())
    val counties: StateFlow<List<County>> = _counties.asStateFlow()

    private val _cities = MutableStateFlow<List<City>>(emptyList())
    val cities: StateFlow<List<City>> = _cities.asStateFlow()

    // Optional: error state for showing user-friendly messages
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun getProvince() {
        viewModelScope.launch {
            val result = getProvince.invoke()
            result.onSuccess { provinces ->
                _provinces.value = provinces
            }.onFailure { error ->
                _error.value = "Failed to load provinces"
            }
        }
    }

    fun getCounty(provinceId: Long) {
        viewModelScope.launch {
            val result = getCounty.invoke(GetCountyUseCase.Params(provinceId = provinceId))
            result.onSuccess { counties ->
                _counties.value = counties
            }.onFailure { error ->
                _error.value = "Failed to load counties"
            }
        }
    }

    fun getCity(provinceId: Long, countyId: Long) {
        viewModelScope.launch {
            val result = getCity.invoke(
                GetCityUseCase.Params(provinceId = provinceId, countyId = countyId)
            )
            result.onSuccess { cities ->
                _cities.value = cities
            }.onFailure { error ->
                _error.value = "Failed to load cities"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}