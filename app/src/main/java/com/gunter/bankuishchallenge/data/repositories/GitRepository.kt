package com.gunter.bankuishchallenge.data.repositories

import com.gunter.bankuishchallenge.domain.model.Repository

interface GitRepository {
    suspend fun getRepositories(query: String, perPage: Int, page: Int): List<Repository>?
}