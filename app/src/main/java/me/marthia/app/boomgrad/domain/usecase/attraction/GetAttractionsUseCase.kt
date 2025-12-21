package me.marthia.app.boomgrad.domain.usecase.attraction

import kotlinx.coroutines.flow.FlowCollector
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.repository.AttractionRepository
import me.marthia.app.boomgrad.domain.util.DataStateUseCase
import me.marthia.app.boomgrad.domain.util.apiCall
import me.marthia.app.boomgrad.presentation.util.DataState

//class GetAttractionsUseCase(
//    private val repository: AttractionRepository
//) {
//    suspend operator fun invoke(): Result<List<Attraction>> {
//        return repository.getAttractions()
//    }
//}

class GetAttractionsUseCase(
    private val repository: AttractionRepository
): DataStateUseCase<Unit, List<Attraction>>() {

    override suspend fun FlowCollector<DataState<List<Attraction>>>.execute(
        params: Unit
    ) {
        val service = apiCall { repository.getAttractions() }

//        emit (service.map { list  -> list.toUi()} )
    }

}