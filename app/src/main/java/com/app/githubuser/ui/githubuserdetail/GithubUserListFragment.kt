package com.app.githubuser.ui.githubuserdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubuser.R
import com.app.githubuser.adapter.ListGithubUserAdapter
import com.app.githubuser.data.Result
import com.app.githubuser.databinding.FragmentGithubUserListBinding
import com.app.githubuser.dataclass.GithubUserListData
import com.app.githubuser.utils.Toaster


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

        val factory = ViewModelFactory.getInstance(requireContext())
        val viewModel: GithubUserDetailViewModel by viewModels { factory }

        if (position == 1) {
            viewModel.getUserFollowers(username)
                .observe(viewLifecycleOwner) { handleMainViewModelResult(requireContext(), it) }
        } else {
            viewModel.getUserFollowing(username)
                .observe(viewLifecycleOwner) { handleMainViewModelResult(requireContext(), it) }
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
                    setListGithubUser(result.data)
                }
                is Result.Error -> {
                    Toaster.short(context, result.error)
                }
            }
        }
    }

    private fun setListGithubUser(listGithubUser: List<GithubUserListData>) {
        showNotFound(listGithubUser.isEmpty())
        val listGithubUserAdapter = ListGithubUserAdapter(listGithubUser)
        val rvListGithubUser = binding.rvListGithubUser
        rvListGithubUser.layoutManager = LinearLayoutManager(context)
        rvListGithubUser.adapter = listGithubUserAdapter

        listGithubUserAdapter.setOnItemClickCallback(object :
            ListGithubUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUserListData) {
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