package com.gunter.bankuishchallenge.data.retrofit

import com.gunter.bankuishchallenge.data.model.ResponseRetrofitGithub
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("search/repositories")
    suspend fun getRepositories(
        @Query("q") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Response<ResponseRetrofitGithub.GetAllRepositories>
}