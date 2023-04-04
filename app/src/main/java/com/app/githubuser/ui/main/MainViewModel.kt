package com.app.githubuser.ui.main

import androidx.lifecycle.ViewModel
import com.app.githubuser.data.GithubUserMainRepository

class MainViewModel(private val githubUserRepository: GithubUserMainRepository) : ViewModel() {
    fun getGithubUserList() = githubUserRepository.getGithubUserList()

    fun getGithubUserSearch(query: String) = githubUserRepository.getGithubUserSearch(query)
}