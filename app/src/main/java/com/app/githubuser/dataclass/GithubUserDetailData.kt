package com.app.githubuser.dataclass

data class GithubUserDetailData(
    val username: String?,
    val avatar: String?,
    val name: String?,
    val bio: Any?,
    val followers: Int?,
    val following: Int?
)