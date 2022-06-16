package com.example.composesample.data.remote.api

import com.example.composesample.data.remote.dto.UserResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubUserService {

    @GET("{userId}")
    suspend fun getUser(
        @Path("userId") userId: String,
    ): UserResponseDto

}