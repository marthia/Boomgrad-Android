package me.marthia.app.boomgrad.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.marthia.app.boomgrad.data.mapper.toDomain
import me.marthia.app.boomgrad.data.remote.api.AttractionApiService
import me.marthia.app.boomgrad.data.remote.util.toNetworkFailure
import me.marthia.app.boomgrad.domain.model.Attraction
import timber.log.Timber

class AttractionPagingDataSource(
    private val apiService: AttractionApiService
) : PagingSource<Int, Attraction>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Attraction> {
        val page = params.key ?: 0

        return apiService.getAttractions(page, params.loadSize)
            .fold(
                onSuccess = { response ->
                    LoadResult.Page(
                        data = response.data.content?.map { it.toDomain() }
                            ?: throw IllegalStateException("content is null"),
                        prevKey = if (page == 0) null else page - 1,
                        nextKey = if (response.data.content.isEmpty()) null else page + 1
                    )
                },
                onFailure = { error ->
                    Timber.e(error, "Failed to load page $page")
                    LoadResult.Error(error.toNetworkFailure())
                }
            )
    }

    override fun getRefreshKey(state: PagingState<Int, Attraction>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}