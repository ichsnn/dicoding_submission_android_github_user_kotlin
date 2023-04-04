package com.app.githubuser.activity.githubuserdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.app.githubuser.R
import com.app.githubuser.databinding.ActivityGithubUserDetailBinding
import com.app.githubuser.dataclass.GithubUserDetailData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class GithubUserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGithubUserDetailBinding

    private val githubUserDetailViewModel by viewModels<GithubUserDetailViewModel>()

    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGithubUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(EXTRA_USERNAME).toString()

        githubUserDetailViewModel.getUserDetail(username)

        githubUserDetailViewModel.userDetail.observe(this) {
            setUserDetail(it)
        }

        githubUserDetailViewModel.isLoading.observe(this) {
            showLoadingPage(it)
        }

        showTabs()
    }

    private fun showLoadingPage(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarPage.visibility = ProgressBar.VISIBLE
            binding.frameLoading.visibility = FrameLayout.VISIBLE
        } else {
            binding.progressBarPage.visibility = ProgressBar.GONE
            binding.frameLoading.visibility = FrameLayout.GONE
        }
    }

    private fun setUserDetail(userDetail: GithubUserDetailData) {
        // Name detail
        binding.apply {
            if (userDetail.name == null) tvName.visibility = TextView.GONE
            else tvName.text = userDetail.name
            // Username detail
            tvUsername.text = userDetail.username
            // Bio detail
            if (userDetail.bio == null) {
                tvBio.visibility = TextView.GONE
            } else tvBio.text = userDetail.bio.toString()
            // Followers count detail
            tvFollowersCount.text = userDetail.followers.toString()
            // Following count detail
            tvFollowingCount.text = userDetail.following.toString()
            Glide.with(this@GithubUserDetailActivity).load(userDetail.avatar).apply(RequestOptions().circleCrop())
                .into(imgAvatar)
        }
    }

    private fun showTabs() {
        val githubUserPagerAdapter = GithubUserPagerAdapter(this)
        githubUserPagerAdapter.username = username
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = githubUserPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = arrayOf(
            R.string.content_tab_followers,
            R.string.content_tab_following
        )
    }
}