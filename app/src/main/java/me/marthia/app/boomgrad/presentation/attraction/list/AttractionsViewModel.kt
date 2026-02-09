package me.marthia.app.boomgrad.presentation.attraction.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.usecase.attraction.GetAttractionsUseCase


class AttractionsViewModel(
    private val getAttractionsUseCase: GetAttractionsUseCase,
) : ViewModel() {

    val attractionsPagingData: Flow<PagingData<Attraction>> =
        getAttractionsUseCase()
            .cachedIn(viewModelScope)

}