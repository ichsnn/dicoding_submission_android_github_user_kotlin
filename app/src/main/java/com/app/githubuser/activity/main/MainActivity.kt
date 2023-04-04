package com.app.githubuser.activity.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubuser.R
import com.app.githubuser.activity.githubuserdetail.GithubUserDetailActivity
import com.app.githubuser.adapter.ListGithubUserAdapter
import com.app.githubuser.databinding.ActivityMainBinding
import com.app.githubuser.dataclass.ListGithubUserData

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        mainViewModel.listGithubUser.observe(this) {
            Log.d(TAG, "onCreate: $it")
            setListGithubUser(it)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search_github_user)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null) {
                    mainViewModel.searchUser(query)
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText == "") mainViewModel.getListUser()
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showNotFound(isNotFound: Boolean) {
        if(isNotFound) {
            binding.tvNotFound.visibility = View.VISIBLE
        } else {
            binding.tvNotFound.visibility = View.GONE
        }
    }

    private fun setListGithubUser(listGithubUser: ArrayList<ListGithubUserData>) {
        showNotFound(listGithubUser.isEmpty())
        val rvListGithubUser = binding.rvListGithubUser
        val listGithubUserAdapter = ListGithubUserAdapter(listGithubUser)
        rvListGithubUser.layoutManager = LinearLayoutManager(this)
        rvListGithubUser.adapter = listGithubUserAdapter

        listGithubUserAdapter.setOnItemClickCallback(object :
            ListGithubUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListGithubUserData) {
                val githubUserDetailIntent = Intent(this@MainActivity, GithubUserDetailActivity::class.java)
                githubUserDetailIntent.putExtra(GithubUserDetailActivity.EXTRA_USERNAME, data.username)
                startActivity(githubUserDetailIntent)
            }
        })
    }
}