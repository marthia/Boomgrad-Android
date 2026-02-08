package me.marthia.app.boomgrad.domain.usecase.attraction

import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.repository.AttractionRepository

class GetAttractionsUseCase(
    private val repository: AttractionRepository
) {
    operator fun invoke(): Flow<PagingData<Attraction>> {
        return repository.getAttractions()
            .cachedIn(CoroutineScope(Dispatchers.IO + SupervisorJob()))
    }
}