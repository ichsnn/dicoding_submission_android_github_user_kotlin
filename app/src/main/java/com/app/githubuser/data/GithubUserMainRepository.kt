package com.app.githubuser.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.app.githubuser.data.remote.retrofit.ApiService
import com.app.githubuser.dataclass.GithubUserListData

class GithubUserMainRepository private constructor(
    private val apiService: ApiService,
) {
    fun getGithubUserList(): LiveData<Result<List<GithubUserListData>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getListUser()
            val newList = response.map {user ->
                GithubUserListData(username = user.login, githubUrl = user.url, avatarUrl = user.avatarUrl)
            }
            emit(Result.Success(newList))
        } catch (e: Exception) {
            Log.d(TAG, "getGithubUserList: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getGithubUserSearch(query: String): LiveData<Result<List<GithubUserListData>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getSearchUser(query)
            val newList = response.items.map {user ->
                GithubUserListData(username = user.login, githubUrl = user.url, avatarUrl = user.avatarUrl)
            }
            emit(Result.Success(newList))
        } catch (e: Exception) {
            Log.d(TAG, "getGithubUserSearch: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        const val TAG = "GithubUserRepository"

        @Volatile
        private var instance: GithubUserMainRepository? = null

        fun getInstance(
            apiService: ApiService,
        ): GithubUserMainRepository = instance ?: synchronized(this) {
            instance ?: GithubUserMainRepository(apiService)
        }.also { instance = it }

    }
}