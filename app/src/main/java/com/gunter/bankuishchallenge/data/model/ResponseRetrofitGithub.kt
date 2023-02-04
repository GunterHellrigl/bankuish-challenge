package com.gunter.bankuishchallenge.data.model

import com.google.gson.annotations.SerializedName
import com.gunter.bankuishchallenge.domain.model.Repository

sealed class ResponseRetrofitGithub {
    data class GetAllRepositories(
        @SerializedName("items")
        val items: List<Repository>
    )

    data class Repository(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("owner")
        val owner: Owner,
        @SerializedName("description")
        val description: String
    )

    data class Owner(
        @SerializedName("login")
        val login: String
    )
}

fun ResponseRetrofitGithub.GetAllRepositories.toDomain() = items.map {
    it.toDomain()
}

fun ResponseRetrofitGithub.Repository.toDomain() = Repository(
    id,
    name,
    author = owner.login,
    description
)
