package com.example.composesample.data.remote.source

import com.example.composesample.data.JobResult
import com.example.composesample.data.remote.api.GithubUserService
import com.example.composesample.data.remote.dto.UserRequestDto
import com.example.composesample.data.remote.dto.UserResponseDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubUserRemoteSource @Inject constructor(
    private val githubUserService: GithubUserService
) {

    suspend fun getUser(userRequestDto: UserRequestDto): JobResult<UserResponseDto> {
        return try {
            val response: UserResponseDto = githubUserService.getUser(userRequestDto.id)
            JobResult.Success(response)
        } catch(e: Exception) {
            JobResult.Error(e)
        }
    }

}