package com.app.githubuser.ui.githubuserdetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.githubuser.data.GithubUserDetailRepository
import com.app.githubuser.di.Injection

internal class ViewModelFactory private constructor(private val githubUserDetailRepository: GithubUserDetailRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GithubUserDetailViewModel::class.java)) {
            return GithubUserDetailViewModel(githubUserDetailRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.githubUserDetailRepository(context))
            }.also { instance = it }
    }
}