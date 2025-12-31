package com.halawany.innovationteamtaskpostsapp.presentation.post.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.halawany.innovationteamtaskpostsapp.core.DateTimeUtils
import com.halawany.innovationteamtaskpostsapp.databinding.ItemPostBinding
import com.halawany.innovationteamtaskpostsapp.domain.model.Post

class PostsAdapter(private val onItemClick: (Post) -> Unit) : PagingDataAdapter<Post, PostsAdapter.PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }

    inner class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            binding.tvTitle.text = post.title

            val date = post.publishedAt?.let { DateTimeUtils.formatShort(it) } ?: "Unknown Date"
            binding.tvMeta.text = date

            Glide.with(binding.root.context).load(post.imageUrl).centerCrop().into(binding.ivPostImage)
            binding.root.setOnClickListener { onItemClick(post) }
        }
    }

    class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }
}
