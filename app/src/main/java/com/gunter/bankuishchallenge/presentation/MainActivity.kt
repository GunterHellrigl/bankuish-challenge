package com.gunter.bankuishchallenge.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.gunter.bankuishchallenge.databinding.ActivityMainBinding
import com.gunter.bankuishchallenge.domain.model.Repository
import com.gunter.bankuishchallenge.presentation.home.HomeFragmentDirections
import com.gunter.bankuishchallenge.presentation.home.OnRepositoryClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnRepositoryClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onRepositoryClick(repository: Repository) {
        val action = HomeFragmentDirections.actionHomeFragmentToRepositoryDetailsFragment(
            Gson().toJson(repository)
        )
        findNavController(binding.navHostFragment.id).navigate(action)
    }
}