package com.gunter.bankuishchallenge.data.datasources

import com.gunter.bankuishchallenge.data.models.ResponseRetrofitGithub
import com.gunter.bankuishchallenge.data.retrofit.RetrofitClient
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GitHubRemoteDataSourceTest {

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
    fun testResponseIsNotSuccessful() = runTest {
        mockkObject(RetrofitClient)

        val response = mockk<Response<ResponseRetrofitGithub.GetAllRepositories>>()

        coEvery {
            RetrofitClient.getGitHubService().getRepositories(any(), any(), any())
        } returns response
        every { response.isSuccessful } returns false


        val github = GitHubRemoteDataSource()
        github.getRepositories("language:kotlin", 20, 1).also {
            Assert.assertNull(it)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testResponseWithException() = runTest {
        mockkObject(RetrofitClient)

        val response = mockk<Response<ResponseRetrofitGithub.GetAllRepositories>>()

        coEvery {
            RetrofitClient.getGitHubService().getRepositories(any(), any(), any())
        } returns response
        every { response.isSuccessful } throws Exception("This is an exception")

        Assert.assertThrows(Exception::class.java) {
            val github = GitHubRemoteDataSource()
            runTest {
                github.getRepositories("language:kotlin", 20, 1).also {
                    Assert.assertNull(it)
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testResponseBodyIsNull() = runTest {
        mockkObject(RetrofitClient)

        val response = mockk<Response<ResponseRetrofitGithub.GetAllRepositories>>()

        coEvery {
            RetrofitClient.getGitHubService().getRepositories(any(), any(), any())
        } returns response
        every { response.isSuccessful } returns true
        every { response.body() } returns null


        val github = GitHubRemoteDataSource()
        github.getRepositories("language:kotlin", 20, 1).also {
            Assert.assertNull(it)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testResponseIsOK() = runTest {
        mockkObject(RetrofitClient)

        val response = mockk<Response<ResponseRetrofitGithub.GetAllRepositories>>()
        val repositories = ResponseRetrofitGithub.GetAllRepositories(
            listOf(
                ResponseRetrofitGithub.Repository(
                    1,
                    "name",
                    ResponseRetrofitGithub.Owner("author"),
                    "dsc"
                )
            )
        )

        coEvery {
            RetrofitClient.getGitHubService().getRepositories(any(), any(), any())
        } returns response
        every { response.isSuccessful } returns true
        every { response.body() } returns repositories


        val github = GitHubRemoteDataSource()
        github.getRepositories("language:kotlin", 20, 1).also {
            Assert.assertNotNull(it)
            Assert.assertEquals(1, it?.size)
            Assert.assertEquals(1, it?.get(0)?.id)
            Assert.assertEquals("name", it?.get(0)?.name)
            Assert.assertEquals("author", it?.get(0)?.author)
            Assert.assertEquals("dsc", it?.get(0)?.description)
        }
    }
}