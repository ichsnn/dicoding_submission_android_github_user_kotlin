package com.app.githubuser.activity.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.githubuser.service.ApiConfig
import com.app.githubuser.pojo.GithubListUserResponseItem
import com.app.githubuser.pojo.GithubSearchResponse
import com.app.githubuser.dataclass.ListGithubUserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _listGithubUser = MutableLiveData<ArrayList<ListGithubUserData>>()
    val listGithubUser: LiveData<ArrayList<ListGithubUserData>> = _listGithubUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        getListUser()
    }

    fun getListUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUser()
        client.enqueue(object : Callback<List<GithubListUserResponseItem>> {
            override fun onResponse(
                call: Call<List<GithubListUserResponseItem>>,
                response: Response<List<GithubListUserResponseItem>>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    val listGithubUser = ArrayList<ListGithubUserData>()
                    result?.map { githubUser ->
                        val githubUserData = ListGithubUserData(
                            avatarUrl = githubUser.avatarUrl.toString(),
                            username = githubUser.login.toString(),
                            githubUrl = githubUser.htmlUrl.toString()
                        )
                        listGithubUser.add(githubUserData)
                    }
                    _listGithubUser.value = listGithubUser
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<List<GithubListUserResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun searchUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearchUser(username)
        client.enqueue(object: Callback<GithubSearchResponse> {
            override fun onResponse(
                call: Call<GithubSearchResponse>,
                response: Response<GithubSearchResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    val listGithubUser = ArrayList<ListGithubUserData>()
                    result?.items?.map { githubUser ->
                        val githubUserData = ListGithubUserData(
                            avatarUrl = githubUser.avatarUrl,
                            username = githubUser.login,
                            githubUrl = githubUser.htmlUrl
                        )
                        listGithubUser.add(githubUserData)
                    }
                    _listGithubUser.value = listGithubUser
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GithubSearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })

    }
}