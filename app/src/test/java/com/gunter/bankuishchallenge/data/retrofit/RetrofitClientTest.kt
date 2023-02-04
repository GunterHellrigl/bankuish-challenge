package com.gunter.bankuishchallenge.data.retrofit

import org.junit.Assert
import org.junit.Test

class RetrofitClientTest {

    @Test
    fun testOk() {
        val service = RetrofitClient.getGitHubService()
        Assert.assertNotNull(service)
    }
}