package com.app.githubuser.activity.githubuserdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.githubuser.dataclass.GithubUserDetailData
import com.app.githubuser.dataclass.ListGithubUserData
import com.app.githubuser.pojo.GithubListUserResponseItem
import com.app.githubuser.pojo.GithubUserDetailResponse
import com.app.githubuser.service.ApiConfig
import retrofit2.Call
import retrofit2.Response

class GithubUserDetailViewModel : ViewModel() {
    private val _cachedUsername = MutableLiveData<String?>()

    private val _userDetail = MutableLiveData<GithubUserDetailData>()
    val userDetail: LiveData<GithubUserDetailData> = _userDetail

    private val _listUser = MutableLiveData<ArrayList<ListGithubUserData>>()
    val listUser: LiveData<ArrayList<ListGithubUserData>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "GithubUserDetailModel"
    }

    fun getUserDetail(username: String) {
        if (username == _cachedUsername.value) return
        _cachedUsername.value = username
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        Log.d(TAG, "getUserDetail: ${client.request().url}")
        client.enqueue(object : retrofit2.Callback<GithubUserDetailResponse> {
            override fun onResponse(
                call: Call<GithubUserDetailResponse>,
                response: Response<GithubUserDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        _userDetail.value = GithubUserDetailData(
                            username = result.login,
                            avatar = result.avatarUrl,
                            name = result.name,
                            bio = result.bio,
                            followers = result.followers,
                            following = result.following
                        )
                    }
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GithubUserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getListFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        Log.d(TAG, "getUserDetail: ${client.request().url}")
        client.enqueue(object : retrofit2.Callback<List<GithubListUserResponseItem>> {
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
                    _listUser.value = listGithubUser
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

    fun getListFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        Log.d(TAG, "getUserDetail: ${client.request().url}")
        client.enqueue(object : retrofit2.Callback<List<GithubListUserResponseItem>> {
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
                    _listUser.value = listGithubUser
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


}