package com.app.githubuser.dataclass

data class GithubUserDetailData(
    val username: String?,
    val avatarUrl: String?,
    val githubUrl: String?,
    val name: String?,
    val bio: Any?,
    val followers: Int = 0,
    val following: Int = 0,
)