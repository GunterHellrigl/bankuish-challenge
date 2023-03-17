package com.gunter.bankuishchallenge.di

import com.gunter.bankuishchallenge.data.datasources.GitHubRemoteDataSource
import com.gunter.bankuishchallenge.data.datasources.GitRemoteDataSource
import com.gunter.bankuishchallenge.data.repositories.GitHubRepository
import com.gunter.bankuishchallenge.domain.repositories.GitRepository
import com.gunter.bankuishchallenge.domain.usecases.GetRepositoriesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideGitRemoteDataSource(): GitRemoteDataSource {
        return GitHubRemoteDataSource()
    }

    @Provides
    fun provideGitRepository(remoteDataSource: GitRemoteDataSource): GitRepository {
        return GitHubRepository(remoteDataSource)
    }

    @Provides
    fun provideGetRepositoriesUseCase(gitRepository: GitRepository): GetRepositoriesUseCase {
        return GetRepositoriesUseCase(gitRepository)
    }
}