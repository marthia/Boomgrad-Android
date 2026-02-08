package me.marthia.app.boomgrad.presentation.attraction.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.usecase.attraction.GetAttractionsUseCase
import me.marthia.app.boomgrad.presentation.util.ViewState


class AttractionsViewModel(
    private val getAttractionsUseCase: GetAttractionsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ViewState<AttractionsUiState>>(ViewState.Idle)
    val uiState: StateFlow<ViewState<AttractionsUiState>> = _uiState.asStateFlow()


    val attractionsPagingData: Flow<PagingData<Attraction>> =
        getAttractionsUseCase()
            .cachedIn(viewModelScope)

}

data class AttractionsUiState(
    val mockList: List<Attraction> = listOf(),
    val list: List<Attraction> = listOf()
)