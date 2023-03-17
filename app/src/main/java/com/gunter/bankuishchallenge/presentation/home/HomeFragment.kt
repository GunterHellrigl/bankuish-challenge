package com.gunter.bankuishchallenge.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.gunter.bankuishchallenge.databinding.FragmentHomeBinding
import com.gunter.bankuishchallenge.presentation.adapters.RepositoriesAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var adapter: RepositoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.setListener {
            val action = HomeFragmentDirections.actionHomeFragmentToRepositoryDetailsFragment(
                Gson().toJson(it)
            )
            findNavController().navigate(action)
        }

        binding.swipeRefresh.setOnRefreshListener {
            adapter.clearData()
            viewModel.getRepositoriesFromStart()
        }
        binding.rvRepositories.adapter = adapter
        binding.rvRepositories.setOnScrollChangeListener { _, _, _, _, _ ->
            val lm: LinearLayoutManager =
                binding.rvRepositories.layoutManager as LinearLayoutManager

            val visibleItemsCount: Int = lm.childCount
            val totalItemsCount: Int = lm.itemCount
            val firstVisibleItemPosition: Int = lm.findFirstVisibleItemPosition()

            if (viewModel.isLoading.value == false && (visibleItemsCount + firstVisibleItemPosition) >= totalItemsCount
                && firstVisibleItemPosition >= 0 && totalItemsCount >= 20
            ) {
                viewModel.getRepositoriesFromNewPage()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = it
        }
        viewModel.showError.observe(viewLifecycleOwner) {
            if (it) {
                Snackbar.make(
                    binding.rvRepositories,
                    "Ups.. something is wrong",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.repositories.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = false
            adapter.setData(it)
        }

        viewModel.getRepositoriesFromStart()
    }
}