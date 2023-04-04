package com.app.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.githubuser.data.local.enitity.FavoriteUserEntity

@Dao
interface GithubUserDao {
    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUser(): LiveData<List<FavoriteUserEntity>>

    @Delete
    suspend fun deleteFavorite(favoriteUser: FavoriteUserEntity)

    @Insert
    suspend fun insertGithubUser(favoriteUser: FavoriteUserEntity)
}