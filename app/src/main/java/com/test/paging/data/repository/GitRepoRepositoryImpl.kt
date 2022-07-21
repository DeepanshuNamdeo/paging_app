package com.test.paging.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.test.paging.data.entities.RepositoryResponseItem
import com.test.paging.data.remote.ApiService
import com.test.paging.data.remote.RepoRemoteDataSourceImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GitRepoRepositoryImpl
@Inject constructor(private val apiService: ApiService) : GitRepoRepository {
    override suspend fun getListOfRepositories(
        page: Int,
        limit: Int
    ): Flow<PagingData<RepositoryResponseItem>> {
        return Pager(
            config = PagingConfig(
                15
            ),
            pagingSourceFactory = {
                RepoRemoteDataSourceImpl(apiService = apiService)
            }
        ).flow
    }


}