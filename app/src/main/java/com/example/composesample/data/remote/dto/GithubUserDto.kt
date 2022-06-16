package com.example.composesample.data.remote.dto

import com.google.gson.annotations.SerializedName


data class UserRequestDto (
    val id: String,
)

data class UserResponseDto (

    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("html_url")
    val htmlUrl: String

)
