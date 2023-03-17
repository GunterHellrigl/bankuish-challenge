package com.gunter.bankuishchallenge.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gunter.bankuishchallenge.domain.models.Repository
import com.gunter.bankuishchallenge.domain.usecases.GetRepositoriesUseCase
import com.gunter.bankuishchallenge.presentation.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
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
        val useCase = mockk<GetRepositoriesUseCase>()
        val repositories = listOf<Repository>()

        coEvery { useCase.execute(any(), any(), any()) } returns repositories

        val viewModel = HomeViewModel(useCase)
        viewModel.getRepositoriesFromStart()

        Assert.assertEquals(viewModel.repositories.value, repositories)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetRepositoriesFromStartWithNull() = runTest {
        val useCase = mockk<GetRepositoriesUseCase>()
        val repositories = listOf<Repository>()

        coEvery { useCase.execute(any(), any(), any()) } returns null

        val viewModel = HomeViewModel(useCase)
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
        val useCase = mockk<GetRepositoriesUseCase>()
        val repositories = listOf<Repository>()

        coEvery { useCase.execute(any(), any(), any()) } returns repositories

        val viewModel = HomeViewModel(useCase)
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
        val useCase = mockk<GetRepositoriesUseCase>()
        val repositories = listOf<Repository>()

        coEvery { useCase.execute(any(), any(), any()) } returns null

        val viewModel = HomeViewModel(useCase)
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