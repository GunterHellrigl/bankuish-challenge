package com.gunter.bankuishchallenge.data.datasources

import com.gunter.bankuishchallenge.domain.models.Repository

interface GitRemoteDataSource {
    suspend fun getRepositories(query: String, perPage: Int, page: Int): List<Repository>?
}