package com.app.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.githubuser.dataclass.ListGithubUserData
import com.app.githubuser.databinding.ItemRowUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListGithubUserAdapter(private val listGithubUser: ArrayList<ListGithubUserData>) :
    RecyclerView.Adapter<ListGithubUserAdapter.ListGithubUserHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListGithubUserHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListGithubUserHolder(binding)
    }

    override fun getItemCount(): Int {
        return listGithubUser.size
    }

    override fun onBindViewHolder(holder: ListGithubUserHolder, position: Int) {
        val user = listGithubUser[position]
        val avatarUrl = user.avatarUrl
        val username = user.username
        val githubUrl = user.githubUrl
        holder.binding.tvUsername.text = username
        holder.binding.tvGithuburl.text = githubUrl
        Glide.with(holder.itemView.context)
            .load(avatarUrl).apply(RequestOptions().circleCrop())
            .into(holder.binding.imgAvatar)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(user)
        }
    }

    inner class ListGithubUserHolder(var binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: ListGithubUserData)
    }
}