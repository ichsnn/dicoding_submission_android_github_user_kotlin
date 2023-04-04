package com.app.githubuser.activity.githubuserdetail

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubuser.R
import com.app.githubuser.adapter.ListGithubUserAdapter
import com.app.githubuser.databinding.FragmentGithubUserListBinding
import com.app.githubuser.dataclass.ListGithubUserData

class GithubUserListFragment : Fragment() {
    private lateinit var binding: FragmentGithubUserListBinding

    private var position: Int = 0
    private var username: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentGithubUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }

        val githubUserDetailViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            )[GithubUserDetailViewModel::class.java]

        if (position == 1) {
            githubUserDetailViewModel.getListFollowers(username)
        } else {
            githubUserDetailViewModel.getListFollowing(username)
        }

        githubUserDetailViewModel.listUser.observe(viewLifecycleOwner) {
            setListGithubUser(it)
        }

        githubUserDetailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setListGithubUser(listGithubUser: ArrayList<ListGithubUserData>) {
        showNotFound(listGithubUser.isEmpty())
        val listGithubUserAdapter = ListGithubUserAdapter(listGithubUser)
        val rvListGithubUser = binding.rvListGithubUser
        rvListGithubUser.layoutManager = LinearLayoutManager(context)
        rvListGithubUser.adapter = listGithubUserAdapter

        listGithubUserAdapter.setOnItemClickCallback(object :
            ListGithubUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListGithubUserData) {
                val githubUserDetailIntent = Intent(context, GithubUserDetailActivity::class.java)
                githubUserDetailIntent.putExtra(
                    GithubUserDetailActivity.EXTRA_USERNAME,
                    data.username
                )
                startActivity(githubUserDetailIntent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) ProgressBar.VISIBLE else ProgressBar.GONE
    }

    private fun showNotFound(isNotFound: Boolean) {
        if (isNotFound) {
            binding.tvNotFound.visibility = TextView.VISIBLE
            if (position == 1) {
                binding.tvNotFound.text =
                    String.format(resources.getString(R.string.user_not_followers), username)
            } else {
                binding.tvNotFound.text =
                    String.format(resources.getString(R.string.user_not_following), username)
            }
        } else {
            binding.tvNotFound.visibility = TextView.GONE
        }
    }

    companion object {
        var ARG_POSITION = "arg_position"
        var ARG_USERNAME = "arg_username"
    }
}