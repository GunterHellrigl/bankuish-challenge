package com.gunter.bankuishchallenge.data.repositories

import com.gunter.bankuishchallenge.data.datasources.GitHubRemoteDataSource
import com.gunter.bankuishchallenge.domain.models.Repository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GitHubRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testNull() = runTest {
        val dataSource = mockk<GitHubRemoteDataSource>()

        coEvery { dataSource.getRepositories(any(), any(), any()) } returns null

        val repository = GitHubRepository(dataSource)
        repository.getRepositories("language:kotlin", 20, 1).also {
            Assert.assertNull(it)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testOK() = runTest {
        val dataSource = mockk<GitHubRemoteDataSource>()
        val repositories = listOf(Repository(1, "name", "author", "dsc"))

        coEvery { dataSource.getRepositories(any(), any(), any()) } returns repositories

        val repository = GitHubRepository(dataSource)
        repository.getRepositories("language:kotlin", 20, 1).also {
            Assert.assertNotNull(it)
            Assert.assertEquals(1, it?.size)
            Assert.assertEquals(1, it?.get(0)?.id)
            Assert.assertEquals("name", it?.get(0)?.name)
            Assert.assertEquals("author", it?.get(0)?.author)
            Assert.assertEquals("dsc", it?.get(0)?.description)
        }
    }
}