package com.gunter.bankuishchallenge.data.datasources

import com.gunter.bankuishchallenge.data.model.toDomain
import com.gunter.bankuishchallenge.data.retrofit.RetrofitClient
import com.gunter.bankuishchallenge.domain.model.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GitHubRemoteDataSource(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : GitRemoteDataSource {
    override suspend fun getRepositories(
        query: String,
        perPage: Int,
        page: Int
    ): List<Repository>? = withContext(dispatcher) {
        try {
            val response =
                RetrofitClient.getGitHubService().getRepositories(query, perPage, page)

            if (response.isSuccessful) {
                response.body()?.toDomain()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}