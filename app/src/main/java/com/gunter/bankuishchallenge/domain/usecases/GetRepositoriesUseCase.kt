package com.gunter.bankuishchallenge.domain.usecases

import com.gunter.bankuishchallenge.domain.models.Repository
import com.gunter.bankuishchallenge.domain.repositories.GitRepository
import javax.inject.Inject

class GetRepositoriesUseCase @Inject constructor(
    private val gitRepository: GitRepository
) {
    suspend fun execute(query: String, perPage: Int, page: Int): List<Repository>? {
        return gitRepository.getRepositories(query, perPage, page)
    }
}