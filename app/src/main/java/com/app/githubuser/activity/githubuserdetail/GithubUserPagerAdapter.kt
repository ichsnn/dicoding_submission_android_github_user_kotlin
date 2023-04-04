package com.app.githubuser.activity.githubuserdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class GithubUserPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username = ""

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = GithubUserListFragment()
        fragment.arguments = Bundle().apply {
            putInt(GithubUserListFragment.ARG_POSITION, position + 1)
            putString(GithubUserListFragment.ARG_USERNAME, username)
        }
        return fragment
    }
}