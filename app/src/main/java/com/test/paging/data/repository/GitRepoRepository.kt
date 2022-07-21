package com.test.paging.data.repository

import androidx.paging.PagingData
import com.test.paging.data.entities.RepositoryResponseItem
import kotlinx.coroutines.flow.Flow

interface GitRepoRepository {
    suspend fun getListOfRepositories(
        page: Int,
        limit: Int
    ): Flow<PagingData<RepositoryResponseItem>>
}