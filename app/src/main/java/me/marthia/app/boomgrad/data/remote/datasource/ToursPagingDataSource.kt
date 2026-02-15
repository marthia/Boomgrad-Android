package me.marthia.app.boomgrad.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.marthia.app.boomgrad.data.mapper.toDomain
import me.marthia.app.boomgrad.data.mapper.toDomainList
import me.marthia.app.boomgrad.data.remote.api.TourApiService
import me.marthia.app.boomgrad.data.remote.util.toNetworkFailure
import me.marthia.app.boomgrad.domain.model.TourDetail
import me.marthia.app.boomgrad.domain.model.TourList
import timber.log.Timber

class ToursPagingDataSource(
    private val apiService: TourApiService
) : PagingSource<Int, TourList>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TourList> {
        val page = params.key ?: 0

        return apiService.getTours(page, params.loadSize)
            .fold(
                onSuccess = { response ->
                    LoadResult.Page(
                        data = response.data.content?.toDomainList()
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

    override fun getRefreshKey(state: PagingState<Int, TourList>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}