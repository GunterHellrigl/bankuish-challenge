package com.gunter.bankuishchallenge.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gunter.bankuishchallenge.databinding.ItemRepositoryBinding
import com.gunter.bankuishchallenge.domain.model.Repository
import javax.inject.Inject

class RepositoriesAdapter @Inject constructor() : RecyclerView.Adapter<RepositoryViewHolder>() {
    private val repositories: MutableList<Repository> = mutableListOf()
    private lateinit var listener: (Repository) -> Unit

    fun clearData() = repositories.clear().also {
        notifyDataSetChanged()
    }

    fun setData(newData: List<Repository>) {
        repositories.addAll(newData).also {
            notifyDataSetChanged()
        }
    }


    fun setListener(listener: (Repository) -> Unit) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder =
        RepositoryViewHolder(
            ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) =
        holder.bind(repositories[position])

    override fun getItemCount(): Int = repositories.size
}

class RepositoryViewHolder(
    private val binding: ItemRepositoryBinding,
    private val listener: (Repository) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(repository: Repository) {
        binding.tvName.text = repository.name
        binding.tvAuthor.text = repository.author

        itemView.setOnClickListener { listener(repository) }
    }
}