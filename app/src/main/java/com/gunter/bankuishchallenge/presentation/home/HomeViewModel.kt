package com.gunter.bankuishchallenge.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gunter.bankuishchallenge.data.repositories.GitRepository
import com.gunter.bankuishchallenge.domain.model.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val hubRepository: GitRepository
) : ViewModel() {

    private var page: Int = 1

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _showError = MutableLiveData<Boolean>()
    val showError: LiveData<Boolean>
        get() = _showError

    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>>
        get() = _repositories

    private fun getRepositories() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val results = hubRepository.getRepositories("kotlin", 20, page)

            _isLoading.postValue(false)

            results?.let {
                _showError.postValue(false)
                _repositories.postValue(it)
            } ?: run {
                if (page == 1) {
                    page = 1
                } else {
                    page--
                }
                _showError.postValue(true)
                _repositories.postValue(emptyList())
            }
        }
    }

    fun getRepositoriesFromStart() {
        page = 1
        getRepositories()
    }

    fun getRepositoriesFromNewPage() {
        page += 1
        getRepositories()
    }
}