package com.example.navigation.presentation.repositories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.navigation.data.entity.GithubRepositoryData
import com.example.navigation.databinding.ItemRepositoryBinding

class RepositoriesAdapter :
    ListAdapter<GithubRepositoryData, RepositoriesAdapter.RepositoriesViewHolder>(repositoryDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RepositoriesViewHolder(
            ItemRepositoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RepositoriesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RepositoriesViewHolder(
        private var binding: ItemRepositoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repository: GithubRepositoryData) {
            binding.repository = repository
            binding.executePendingBindings()
        }
    }

    companion object {
        private val repositoryDiffUtil = object : DiffUtil.ItemCallback<GithubRepositoryData>() {
            override fun areItemsTheSame(
                oldItem: GithubRepositoryData,
                newItem: GithubRepositoryData
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: GithubRepositoryData,
                newItem: GithubRepositoryData
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
