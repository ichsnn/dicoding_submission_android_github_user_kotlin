package com.app.githubuser.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubuser.R
import com.app.githubuser.adapter.ListGithubUserAdapter
import com.app.githubuser.data.local.enitity.FavoriteUserEntity
import com.app.githubuser.databinding.ActivityFavoriteBinding
import com.app.githubuser.dataclass.GithubUserListData
import com.app.githubuser.ui.githubuserdetail.GithubUserDetailActivity

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    private lateinit var factory: ViewModelFactory
    private val viewModel: FavoriteViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        factory = ViewModelFactory.getInstance(this)

        viewModel.getFavoriteUser().observe(this) { handleFavoriteUserResult(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleFavoriteUserResult(
        result: List<FavoriteUserEntity>?
    ) {
        binding.progressBar.visibility = View.GONE
        val githubUserData = result?.map { user ->
            GithubUserListData(user.username, user.avatarUrl, user.githubUrl)
        }
        if (githubUserData != null)
            setListGithubUser(githubUserData)
    }

    private fun showNotFound(isNotFound: Boolean) {
        if (isNotFound) {
            binding.tvNotFound.visibility = View.VISIBLE
        } else {
            binding.tvNotFound.visibility = View.GONE
        }
    }

    private fun setListGithubUser(listGithubUser: List<GithubUserListData>) {
        showNotFound(listGithubUser.isEmpty())
        val rvListGithubUser = binding.rvListGithubUser
        val listGithubUserAdapter = ListGithubUserAdapter(listGithubUser)
        rvListGithubUser.layoutManager = LinearLayoutManager(this)
        rvListGithubUser.adapter = listGithubUserAdapter

        listGithubUserAdapter.setOnItemClickCallback(object :
            ListGithubUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUserListData) {
                val githubUserDetailIntent =
                    Intent(this@FavoriteActivity, GithubUserDetailActivity::class.java)
                githubUserDetailIntent.putExtra(
                    GithubUserDetailActivity.EXTRA_USERNAME,
                    data.username
                )
                startActivity(githubUserDetailIntent)
            }
        })
    }
}