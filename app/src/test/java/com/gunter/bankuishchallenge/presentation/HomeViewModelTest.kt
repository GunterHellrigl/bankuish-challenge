package com.gunter.bankuishchallenge.presentation

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gunter.bankuishchallenge.data.model.ResponseRetrofitGithub
import com.gunter.bankuishchallenge.data.repositories.GitHubRepository
import com.gunter.bankuishchallenge.domain.model.Repository
import com.gunter.bankuishchallenge.presentation.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*

class HomeViewModelTest {

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
    fun testGetRepositoriesFromStart() = runTest {
        val repository = mockk<GitHubRepository>()
        val repositories = listOf<Repository>()

        coEvery { repository.getRepositories(any(), any(), any()) } returns repositories

        val viewModel = HomeViewModel(repository)
        viewModel.getRepositoriesFromStart()

        Assert.assertEquals(viewModel.repositories.value, repositories)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetRepositoriesFromStartWithNull() = runTest {
        val repository = mockk<GitHubRepository>()
        val repositories = listOf<Repository>()

        coEvery { repository.getRepositories(any(), any(), any()) } returns null

        val viewModel = HomeViewModel(repository)
        viewModel.getRepositoriesFromStart()

        val pageField = viewModel.javaClass.getDeclaredField("page")
        pageField.isAccessible = true
        val page: Int = pageField.get(viewModel) as Int


        Assert.assertEquals(emptyList<Repository>(), repositories)
        Assert.assertEquals(1, page)
        Assert.assertEquals(viewModel.repositories.value, emptyList<Repository>())
        Assert.assertEquals(viewModel.showError.value, true)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetRepositoriesFromNewPage() = runTest {
        val repository = mockk<GitHubRepository>()
        val repositories = listOf<Repository>()

        coEvery { repository.getRepositories(any(), any(), any()) } returns repositories

        val viewModel = HomeViewModel(repository)
        viewModel.getRepositoriesFromNewPage()

        val pageField = viewModel.javaClass.getDeclaredField("page")
        pageField.isAccessible = true
        val page: Int = pageField.get(viewModel) as Int

        Assert.assertEquals(page, 2)
        Assert.assertEquals(viewModel.repositories.value, repositories)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetRepositoriesFromNewPageWithNull() = runTest {
        val repository = mockk<GitHubRepository>()
        val repositories = listOf<Repository>()

        coEvery { repository.getRepositories(any(), any(), any()) } returns null

        val viewModel = HomeViewModel(repository)
        viewModel.getRepositoriesFromNewPage()

        val pageField = viewModel.javaClass.getDeclaredField("page")
        pageField.isAccessible = true
        val page: Int = pageField.get(viewModel) as Int


        Assert.assertEquals(emptyList<Repository>(), repositories)
        Assert.assertEquals(1, page)
        Assert.assertEquals(viewModel.repositories.value, emptyList<Repository>())
        Assert.assertEquals(viewModel.showError.value, true)
    }
}