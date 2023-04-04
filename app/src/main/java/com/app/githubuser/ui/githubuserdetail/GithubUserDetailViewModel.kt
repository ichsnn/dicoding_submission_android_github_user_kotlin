package com.app.githubuser.ui.githubuserdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.githubuser.data.GithubUserDetailRepository
import com.app.githubuser.data.local.enitity.FavoriteUserEntity
import kotlinx.coroutines.launch

class GithubUserDetailViewModel(private val githubUserDetailRepository: GithubUserDetailRepository) :
    ViewModel() {
    fun getUserDetail(username: String) = githubUserDetailRepository.getGithubUserDetail(username)
    fun getUserFollowers(username: String) =
        githubUserDetailRepository.getGithubUserFollowers(username)

    fun getUserFollowing(username: String) =
        githubUserDetailRepository.getGithubUserFollowing(username)

    fun addFavoriteUser(favoriteUser: FavoriteUserEntity) {
        viewModelScope.launch {
            githubUserDetailRepository.addFavoriteUser(favoriteUser)
        }
    }

    fun removeFavoriteUser(favoriteUser: FavoriteUserEntity) {
        viewModelScope.launch {
            githubUserDetailRepository.removeFavoriteUser(favoriteUser)
        }
    }

    fun isFavoriteUser(username: String) = githubUserDetailRepository.isFavoriteUser(username)
}