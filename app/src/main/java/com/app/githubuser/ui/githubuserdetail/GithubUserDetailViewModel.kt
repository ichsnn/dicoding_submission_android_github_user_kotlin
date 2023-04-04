package com.app.githubuser.ui.githubuserdetail

import androidx.lifecycle.ViewModel

class GithubUserDetailViewModel : ViewModel() {
    // private val _cachedUsername = MutableLiveData<String?>()
    //
    // private val _userDetail = MutableLiveData<GithubUserDetailEntity>()
    // val userDetail: LiveData<GithubUserDetailEntity> = _userDetail
    //
    // private val _listUser = MutableLiveData<ArrayList<GithubUserEntity>>()
    // val listUser: LiveData<ArrayList<GithubUserEntity>> = _listUser
    //
    // private val _isLoading = MutableLiveData<Boolean>()
    // val isLoading: LiveData<Boolean> = _isLoading
    //
    // companion object {
    //     private const val TAG = "GithubUserDetailModel"
    // }
    //
    // fun getUserDetail(username: String) {
    //     if (username == _cachedUsername.value) return
    //     _cachedUsername.value = username
    //     _isLoading.value = true
    //     val client = ApiConfig.getApiService().getDetailUser(username)
    //     Log.d(TAG, "getUserDetail: ${client.request().url}")
    //     client.enqueue(object : retrofit2.Callback<GithubUserDetailResponse> {
    //         override fun onResponse(
    //             call: Call<GithubUserDetailResponse>,
    //             response: Response<GithubUserDetailResponse>
    //         ) {
    //             if (response.isSuccessful) {
    //                 val result = response.body()
    //                 if (result != null) {
    //                     _userDetail.value = GithubUserDetailEntity(
    //                         username = result.login,
    //                         avatarUrl = result.avatarUrl,
    //                         name = result.name,
    //                         bio = result.bio,
    //                         followers = result.followers,
    //                         following = result.following
    //                     )
    //                 }
    //             } else {
    //                 Log.e(TAG, "onResponse: ${response.message()}")
    //             }
    //             _isLoading.value = false
    //         }
    //
    //         override fun onFailure(call: Call<GithubUserDetailResponse>, t: Throwable) {
    //             _isLoading.value = false
    //             Log.e(TAG, "onFailure: ${t.message.toString()}")
    //         }
    //     })
    // }
    //
    // fun getListFollowers(username: String) {
    //     _isLoading.value = true
    //     val client = ApiConfig.getApiService().getFollowers(username)
    //     Log.d(TAG, "getUserDetail: ${client.request().url}")
    //     client.enqueue(object : retrofit2.Callback<List<GithubListUserResponseItem>> {
    //         override fun onResponse(
    //             call: Call<List<GithubListUserResponseItem>>,
    //             response: Response<List<GithubListUserResponseItem>>
    //         ) {
    //             if (response.isSuccessful) {
    //                 val result = response.body()
    //                 val listGithubUser = ArrayList<GithubUserEntity>()
    //                 result?.map { githubUser ->
    //                     val githubUserData = GithubUserEntity(
    //                         avatarUrlToImage = githubUser.avatarUrl.toString(),
    //                         username = githubUser.login.toString(),
    //                         githubUrl = githubUser.htmlUrl.toString()
    //                     )
    //                     listGithubUser.add(githubUserData)
    //                 }
    //                 _listUser.value = listGithubUser
    //             } else {
    //                 Log.e(TAG, "onResponse: ${response.message()}")
    //             }
    //             _isLoading.value = false
    //         }
    //
    //         override fun onFailure(call: Call<List<GithubListUserResponseItem>>, t: Throwable) {
    //             _isLoading.value = false
    //             Log.e(TAG, "onFailure: ${t.message.toString()}")
    //         }
    //
    //     })
    // }
    //
    // fun getListFollowing(username: String) {
    //     _isLoading.value = true
    //     val client = ApiConfig.getApiService().getFollowing(username)
    //     Log.d(TAG, "getUserDetail: ${client.request().url}")
    //     client.enqueue(object : retrofit2.Callback<List<GithubListUserResponseItem>> {
    //         override fun onResponse(
    //             call: Call<List<GithubListUserResponseItem>>,
    //             response: Response<List<GithubListUserResponseItem>>
    //         ) {
    //             if (response.isSuccessful) {
    //                 val result = response.body()
    //                 val listGithubUser = ArrayList<GithubUserEntity>()
    //                 result?.map { githubUser ->
    //                     val githubUserData = GithubUserEntity(
    //                         avatarUrlToImage = githubUser.avatarUrl.toString(),
    //                         username = githubUser.login.toString(),
    //                         githubUrl = githubUser.htmlUrl.toString()
    //                     )
    //                     listGithubUser.add(githubUserData)
    //                 }
    //                 _listUser.value = listGithubUser
    //             } else {
    //                 Log.e(TAG, "onResponse: ${response.message()}")
    //             }
    //             _isLoading.value = false
    //         }
    //
    //         override fun onFailure(call: Call<List<GithubListUserResponseItem>>, t: Throwable) {
    //             _isLoading.value = false
    //             Log.e(TAG, "onFailure: ${t.message.toString()}")
    //         }
    //
    //     })
    // }
}