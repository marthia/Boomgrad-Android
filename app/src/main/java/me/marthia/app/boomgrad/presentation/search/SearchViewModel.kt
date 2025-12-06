package me.marthia.app.boomgrad.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.repository.AttractionRepository


class SearchViewModel(
    private val repository: AttractionRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Attraction>>(emptyList())
    val searchResults: StateFlow<List<Attraction>> = _searchResults.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        if (query.length >= 2) {
            searchAttractions(query)
        } else {
            _searchResults.value = emptyList()
        }
    }

    private fun searchAttractions(query: String) {
        viewModelScope.launch {
            _isSearching.value = true
            repository.searchAttractions(query)
                .onSuccess { results ->
                    _searchResults.value = results
                }
                .onFailure {
                    _searchResults.value = emptyList()
                }
            _isSearching.value = false
        }
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _searchResults.value = emptyList()
    }
}