package com.gunter.bankuishchallenge.data.repositories

import com.gunter.bankuishchallenge.data.datasources.GitRemoteDataSource
import com.gunter.bankuishchallenge.domain.model.Repository
import javax.inject.Inject

class GitHubRepository @Inject constructor(
    private val remoteDataSource: GitRemoteDataSource
) : GitRepository {
    override suspend fun getRepositories(
        query: String,
        perPage: Int,
        page: Int
    ): List<Repository>? {
        return remoteDataSource.getRepositories(query, perPage, page)
    }
}