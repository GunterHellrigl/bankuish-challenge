package com.gunter.bankuishchallenge.data.datasources

import com.gunter.bankuishchallenge.domain.model.Repository

interface GitRemoteDataSource {
    suspend fun getRepositories(query: String, perPage: Int, page: Int): List<Repository>?
}