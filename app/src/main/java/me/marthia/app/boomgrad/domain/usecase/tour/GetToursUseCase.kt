package me.marthia.app.boomgrad.domain.usecase.tour

import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import me.marthia.app.boomgrad.domain.model.TourList
import me.marthia.app.boomgrad.domain.repository.TourRepository

class GetToursUseCase(
    private val repository: TourRepository
) {
    operator fun invoke(): Flow<PagingData<TourList>> {
        return repository.getAllActiveTours()
            .cachedIn(CoroutineScope(Dispatchers.IO + SupervisorJob()))
    }
}