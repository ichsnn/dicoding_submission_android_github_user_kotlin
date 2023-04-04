package com.app.githubuser.ui.favorite

import androidx.lifecycle.ViewModel
import com.app.githubuser.data.GithubUserDetailRepository

class FavoriteViewModel(private val githubUserDetailRepository: GithubUserDetailRepository): ViewModel() {
    fun getFavoriteUser() = githubUserDetailRepository.getFavoriteUser()
}