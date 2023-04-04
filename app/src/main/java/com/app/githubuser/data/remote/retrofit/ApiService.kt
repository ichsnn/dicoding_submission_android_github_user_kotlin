package com.app.githubuser.data.remote.retrofit

import com.app.githubuser.data.remote.response.GithubListUserResponseItem
import com.app.githubuser.data.remote.response.GithubSearchResponse
import com.app.githubuser.data.remote.response.GithubUserDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getListUser(): List<GithubListUserResponseItem>

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): GithubUserDetailResponse

    @GET("search/users")
    suspend fun getSearchUser(
        @Query("q") query: String
    ): GithubSearchResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(@Path("username") username: String): List<GithubListUserResponseItem>

    @GET("users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String): List<GithubListUserResponseItem>
}