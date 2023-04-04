package com.app.githubuser.di

import android.content.Context
import com.app.githubuser.data.GithubUserMainRepository
import com.app.githubuser.data.local.room.GithubUserDatabase
import com.app.githubuser.data.remote.retrofit.ApiConfig
import com.app.githubuser.utils.AppExecutors

object Injection {
    fun githubUserMainRepository(): GithubUserMainRepository {
        val apiService = ApiConfig.getApiService()
        return GithubUserMainRepository.getInstance(apiService)
    }
}