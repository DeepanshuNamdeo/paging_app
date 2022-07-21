package com.test.paging.data.remote

import com.test.paging.data.entities.RepositoryResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("repos")
    suspend fun getListOfRepositories(
        @Query("page") page: Int,
        @Query("per_page") limit: Int
    ): RepositoryResponse

}