package com.app.githubuser.ui.githubuserdetail

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import com.app.githubuser.R
import com.app.githubuser.data.Result
import com.app.githubuser.data.local.enitity.FavoriteUserEntity
import com.app.githubuser.databinding.ActivityGithubUserDetailBinding
import com.app.githubuser.dataclass.GithubUserDetailData
import com.app.githubuser.utils.Toaster
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class GithubUserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGithubUserDetailBinding

    private lateinit var factory: ViewModelFactory
    private val viewModel: GithubUserDetailViewModel by viewModels { factory }

    private var userDetail: GithubUserDetailData? = null

    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGithubUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        factory = ViewModelFactory.getInstance(this)

        username = intent.getStringExtra(EXTRA_USERNAME).toString()

        viewModel.getUserDetail(username)
            .observe(this) { handleUserDetail(this, it) }

        viewModel.isFavoriteUser(username).observe(this) { state ->
            binding.fabFavorite.setImageResource(if (state) R.drawable.ic_favorite else R.drawable.ic_favorite_border)
        }

        binding.fabFavorite.setOnClickListener {
            onFavoriteClick(this@GithubUserDetailActivity, userDetail)
        }

        showTabs()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onFavoriteClick(owner: LifecycleOwner, data: GithubUserDetailData?) {
        data?.let {
            val favoriteUser = FavoriteUserEntity(
                username = username,
                avatarUrl = it.avatarUrl,
                githubUrl = it.githubUrl
            )
            viewModel.isFavoriteUser(username).observe(owner) { isFavorite ->
                if (isFavorite) {
                    viewModel.removeFavoriteUser(
                        favoriteUser
                    )
                    binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
                } else {
                    viewModel.addFavoriteUser(favoriteUser)
                    binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
                }
            }
        }
    }

    private fun handleUserDetail(context: Context, result: Result<GithubUserDetailData>?) {
        if (result != null) {
            when (result) {
                is Result.Loading -> {
                    showLoadingPage(true)
                }
                is Result.Success -> {
                    showLoadingPage(false)
                    userDetail = result.data
                    setUserDetail(result.data)
                }
                is Result.Error -> {
                    showLoadingPage(false)
                    Toaster.short(context, result.error)
                }
            }
        }
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
            Glide.with(this@GithubUserDetailActivity).load(userDetail.avatarUrl)
                .apply(RequestOptions().circleCrop())
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