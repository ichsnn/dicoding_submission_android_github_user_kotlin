package com.app.githubuser.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.app.githubuser.data.local.enitity.FavoriteUserEntity
import com.app.githubuser.data.local.room.GithubUserDao
import com.app.githubuser.data.remote.retrofit.ApiService
import com.app.githubuser.dataclass.GithubUserDetailData
import com.app.githubuser.dataclass.GithubUserListData

class GithubUserDetailRepository private constructor(
    private val apiService: ApiService,
    private val githubUserDao: GithubUserDao
) {
    fun getGithubUserDetail(username: String): LiveData<Result<GithubUserDetailData>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailUser(username)
            var result: GithubUserDetailData
            response.apply {
                result = GithubUserDetailData(
                    username = login,
                    avatarUrl = avatarUrl,
                    githubUrl = url,
                    name = name,
                    bio = bio,
                    followers = followers as Int,
                    following as Int,
                )
            }
            emit(Result.Success(result))
        } catch (e: Exception) {
            Log.d(TAG, "getGithubUserDetail: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getGithubUserFollowers(username: String): LiveData<Result<List<GithubUserListData>>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.getFollowers(username)
                val newList = response.map { user ->
                    GithubUserListData(
                        username = user.login,
                        githubUrl = user.url,
                        avatarUrl = user.avatarUrl
                    )
                }
                emit(Result.Success(newList))
            } catch (e: Exception) {
                Log.d(TAG, "getGithubUserFollowers: ${e.message.toString()}")
                emit(Result.Error(e.message.toString()))
            }
        }

    fun getGithubUserFollowing(username: String): LiveData<Result<List<GithubUserListData>>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.getFollowing(username)
                val newList = response.map { user ->
                    GithubUserListData(
                        username = user.login,
                        githubUrl = user.url,
                        avatarUrl = user.avatarUrl
                    )
                }
                emit(Result.Success(newList))
            } catch (e: Exception) {
                Log.d(TAG, "getGithubUserFollowers: ${e.message.toString()}")
                emit(Result.Error(e.message.toString()))
            }
        }

    suspend fun addFavoriteUser(favoriteUser: FavoriteUserEntity) {
        githubUserDao.insertFavorite(favoriteUser)
    }

    suspend fun removeFavoriteUser(favoriteUser: FavoriteUserEntity) {
        githubUserDao.deleteFavorite(favoriteUser)
    }

    fun getFavoriteUser() = githubUserDao.getFavoriteUser()

    fun isFavoriteUser(username: String): LiveData<Boolean> = liveData {
        emit(githubUserDao.isFavoriteUser(username))
    }

    companion object {
        const val TAG = "GithubUserDetailRepository"

        @Volatile
        private var instance: GithubUserDetailRepository? = null

        fun getInstance(
            apiService: ApiService,
            githubUserDao: GithubUserDao
        ): GithubUserDetailRepository = instance ?: synchronized(this) {
            instance ?: GithubUserDetailRepository(
                apiService,
                githubUserDao
            )
        }.also { instance = it }
    }
}