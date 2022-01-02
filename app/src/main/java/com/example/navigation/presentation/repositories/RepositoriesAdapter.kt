package com.example.navigation.presentation.repositories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.navigation.data.entity.GithubRepositoryData
import com.example.navigation.databinding.ItemRepositoryBinding
import com.example.navigation.util.BaseDiffUtilItemCallback

class RepositoriesAdapter :
    PagingDataAdapter<GithubRepositoryData, RepositoriesAdapter.RepositoriesViewHolder>(
        BaseDiffUtilItemCallback(
            itemsTheSame = { oldItem, newItem -> oldItem.name == newItem.name },
            contentsTheSame = { oldItem, newItem -> oldItem == newItem })
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRepositoryBinding.inflate(inflater, parent, false)
        return RepositoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoriesViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class RepositoriesViewHolder(
        private var binding: ItemRepositoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repository: GithubRepositoryData) {
            binding.repository = repository
            binding.executePendingBindings()
        }
    }
}
