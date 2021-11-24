package com.example.astronomyexercise.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astronomyexercise.data.models.NasaItem
import com.example.astronomyexercise.data.models.NasaResponse
import com.example.astronomyexercise.util.DispatcherProvider
import com.example.astronomyexercise.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    val nasaSuccess = MutableLiveData<Resource<NasaResponse>>()
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    var allUsers: MutableLiveData<List<NasaItem>> = MutableLiveData()
    val nasaError: MutableLiveData<Boolean> = MutableLiveData()

    fun callApi() {
        viewModelScope.launch(dispatchers.io) {
            loading.postValue(true)
            when (val response = repository.getImages("DEMO_KEY")) {
                is Resource.Error -> {
                    loading.postValue(false)
                    nasaError.postValue(true)
                }
                is Resource.Success -> {
                    nasaSuccess.postValue(response)
                    insertIntoDb(response)
                    loading.postValue(false)
                }
            }
        }
    }

    private suspend fun insertIntoDb(nasaResponse: Resource<NasaResponse>) {
        try {
            repository.insertImage(
                NasaItem(
                    nasaResponse.data?.title,
                    nasaResponse.data?.explanation,
                    nasaResponse.data?.url
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getAllImages() {
        viewModelScope.launch {
            loading.postValue(true)
            val list = repository.getImagesFromDb()
            allUsers.postValue(list!!)
            loading.postValue(false)
        }
    }
}