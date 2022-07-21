package com.test.paging.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.paging.data.entities.RepositoryResponseItem
import com.test.paging.data.repository.GitRepoRepository

class RepoRemoteDataSourceImpl
constructor(private val apiService: ApiService ) : PagingSource<Int, RepositoryResponseItem>(),
    RepoRemoteDataSource {

    override fun getRefreshKey(state: PagingState<Int, RepositoryResponseItem>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryResponseItem> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getListOfRepositories(page, 15)
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (response.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}