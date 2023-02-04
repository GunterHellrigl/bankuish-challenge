package com.gunter.bankuishchallenge.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gunter.bankuishchallenge.domain.model.Repository
import com.gunter.bankuishchallenge.presentation.repositorydetails.RepositoryDetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*

class RepositoryDetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

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
    fun testGetRepositoriesFromNewPageWithNull() = runTest {
        val repo = Repository(1, "name", "author", "dsc")
        val viewModel = RepositoryDetailsViewModel()
        viewModel.getRepository(repo)

        Assert.assertEquals(viewModel.repository.value, repo)
        Assert.assertEquals(viewModel.isLoading.value, false)
    }
}