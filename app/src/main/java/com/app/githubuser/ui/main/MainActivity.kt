package com.app.githubuser.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubuser.R
import com.app.githubuser.ui.githubuserdetail.GithubUserDetailActivity
import com.app.githubuser.adapter.ListGithubUserAdapter
import com.app.githubuser.databinding.ActivityMainBinding
import com.app.githubuser.dataclass.GithubUserListData
import com.app.githubuser.data.Result
import com.app.githubuser.data.SettingPreferences
import com.app.githubuser.ui.favorite.FavoriteActivity
import com.app.githubuser.ui.setting.SettingActivity
import com.app.githubuser.ui.setting.SettingViewModel
import com.app.githubuser.ui.setting.SettingViewModelFactory
import com.app.githubuser.utils.Toaster

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var factory: ViewModelFactory
    private val viewModel: MainViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(pref)
        )[SettingViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        factory = ViewModelFactory.getInstance()

        viewModel.getGithubUserList().observe(this) { handleMainViewModelResult(this, it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search_github_user)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.getGithubUserSearch(query)
                        .observe(this@MainActivity) {
                            handleMainViewModelResult(
                                this@MainActivity,
                                it
                            )
                        }
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == "") viewModel.getGithubUserList()
                    .observe(this@MainActivity) { handleMainViewModelResult(this@MainActivity, it) }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                startActivity(Intent(applicationContext, FavoriteActivity::class.java))
            }
            R.id.settings -> {
                startActivity(Intent(applicationContext, SettingActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showNotFound(isNotFound: Boolean) {
        if (isNotFound) {
            binding.tvNotFound.visibility = View.VISIBLE
        } else {
            binding.tvNotFound.visibility = View.GONE
        }
    }

    private fun handleMainViewModelResult(
        context: Context,
        result: Result<List<GithubUserListData>>?
    ) {
        if (result != null) {
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    val githubUserData = result.data
                    setListGithubUser(githubUserData)
                }
                is Result.Error -> {
                    Toaster.short(context, result.error)
                }
            }
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
                    Intent(this@MainActivity, GithubUserDetailActivity::class.java)
                githubUserDetailIntent.putExtra(
                    GithubUserDetailActivity.EXTRA_USERNAME,
                    data.username
                )
                startActivity(githubUserDetailIntent)
            }
        })
    }
}