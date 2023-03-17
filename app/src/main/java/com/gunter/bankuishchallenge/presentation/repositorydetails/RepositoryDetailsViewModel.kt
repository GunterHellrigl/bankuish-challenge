package com.gunter.bankuishchallenge.presentation.repositorydetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gunter.bankuishchallenge.domain.models.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryDetailsViewModel @Inject constructor() : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _repository = MutableLiveData<Repository>()
    val repository: LiveData<Repository>
        get() = _repository

    fun getRepository(repository: Repository) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            _repository.postValue(repository) // It could be a request api to get all data about a repository
            _isLoading.postValue(false)
        }
    }
}