package com.gunter.bankuishchallenge.presentation.repositorydetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.gunter.bankuishchallenge.R
import com.gunter.bankuishchallenge.databinding.FragmentRepositorydetailsBinding
import com.gunter.bankuishchallenge.domain.models.Repository
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryDetailsFragment : Fragment() {

    private val viewModel: RepositoryDetailsViewModel by viewModels()
    private lateinit var binding: FragmentRepositorydetailsBinding

    private val args: RepositoryDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositorydetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.isVisible = true
                binding.mainContainer.isVisible = false
            } else {
                binding.progressBar.isVisible = false
                binding.mainContainer.isVisible = true
            }
        }
        viewModel.repository.observe(viewLifecycleOwner) {
            binding.tvId.text = String.format(resources.getString(R.string.tv_id), it.id)
            binding.tvName.text = it.name
            binding.tvAuthor.text = it.author
            binding.tvDsc.text = it.description
        }

        val repository: Repository = Gson().fromJson(args.repository, Repository::class.java)
        viewModel.getRepository(repository)
    }
}