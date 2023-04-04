package com.app.githubuser.service

import com.app.githubuser.pojo.GithubListUserResponseItem
import com.app.githubuser.pojo.GithubSearchResponse
import com.app.githubuser.pojo.GithubUserDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getListUser(): Call<List<GithubListUserResponseItem>>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<GithubUserDetailResponse>

    @GET("search/users")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<GithubSearchResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<GithubListUserResponseItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<GithubListUserResponseItem>>
}