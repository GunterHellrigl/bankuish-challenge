package com.gunter.bankuishchallenge.domain.repositories

import com.gunter.bankuishchallenge.domain.models.Repository

interface GitRepository {
    suspend fun getRepositories(query: String, perPage: Int, page: Int): List<Repository>?
}