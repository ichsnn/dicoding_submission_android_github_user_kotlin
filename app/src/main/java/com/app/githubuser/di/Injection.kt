package com.app.githubuser.di

import android.content.Context
import com.app.githubuser.data.GithubUserDetailRepository
import com.app.githubuser.data.GithubUserMainRepository
import com.app.githubuser.data.local.room.GithubUserDatabase
import com.app.githubuser.data.remote.retrofit.ApiConfig

object Injection {
    fun githubUserMainRepository(): GithubUserMainRepository {
        val apiService = ApiConfig.getApiService()
        return GithubUserMainRepository.getInstance(apiService)
    }

    fun githubUserDetailRepository(context: Context): GithubUserDetailRepository {
        val apiService = ApiConfig.getApiService()
        val database = GithubUserDatabase.getInstance(context)
        val dao = database.githubUserDao()
        return GithubUserDetailRepository.getInstance(apiService, dao)
    }
}