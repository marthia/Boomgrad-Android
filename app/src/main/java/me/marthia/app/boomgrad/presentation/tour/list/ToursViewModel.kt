package me.marthia.app.boomgrad.presentation.tour.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import me.marthia.app.boomgrad.domain.model.TourList
import me.marthia.app.boomgrad.domain.usecase.tour.GetToursUseCase


class ToursViewModel(
    private val getAllTours: GetToursUseCase,
) : ViewModel() {

    val toursPagingData: Flow<PagingData<TourList>> =
        getAllTours()
            .cachedIn(viewModelScope)

}