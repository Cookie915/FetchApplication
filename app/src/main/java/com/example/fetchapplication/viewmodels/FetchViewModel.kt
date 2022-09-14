package com.example.fetchapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchapplication.repos.FetchRepository
import com.example.fetchapplication.model.data.FetchData
import com.example.fetchapplication.model.data.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FetchViewModel @Inject constructor(
    private val fetchRepository: FetchRepository
): ViewModel() {
    private val _fetchData: MutableStateFlow<NetworkResult<List<FetchData>>> = MutableStateFlow(NetworkResult.Loading())
    val fetchData: StateFlow<NetworkResult<List<FetchData>>> = _fetchData.asStateFlow()

    private val _listOne: MutableStateFlow<List<FetchData>> = MutableStateFlow(listOf())
    val listOne: StateFlow<List<FetchData>> = _listOne.asStateFlow()

    private val _listTwo: MutableStateFlow<List<FetchData>> = MutableStateFlow(listOf())
    val listTwo: StateFlow<List<FetchData>> = _listTwo.asStateFlow()

    private val _listThree: MutableStateFlow<List<FetchData>> = MutableStateFlow(listOf())
    val listThree: StateFlow<List<FetchData>> = _listThree.asStateFlow()

    private val _listFour: MutableStateFlow<List<FetchData>> = MutableStateFlow(listOf())
    val listFour: StateFlow<List<FetchData>> = _listFour.asStateFlow()

    //  Attempts to fetch data from server, and update state appropriately
    fun getFetchData() = viewModelScope.launch {
        _fetchData.value = NetworkResult.Loading()
        fetchRepository.getFetchData().collect { result ->
            //  on success filter data and update individual lists
            if (result is NetworkResult.Success) {
                val filteredList = result.data!!.filter {
                    !it.name.isNullOrEmpty()
                }
                val listOne = filteredList.filter {
                    it.listId == 1
                }.sortedBy {
                    it.id
                }
                val listTwo = filteredList.filter {
                    it.listId == 2
                }.sortedBy {
                    it.id
                }
                val listThree = filteredList.filter {
                    it.listId == 3
                }.sortedBy {
                    it.id
                }
                val listFour = filteredList.filter {
                    it.listId == 4
                }.sortedBy {
                    it.id
                }
                val newList = concatenate(listOne, listTwo, listThree, listFour)
                val newResult = NetworkResult.Success(newList)
                _listOne.value = listOne
                _listTwo.value = listTwo
                _listThree.value = listThree
                _listFour.value = listFour
                _fetchData.value = newResult
                return@collect
            }
            _fetchData.value = result
        }
    }

    // takes list of lists and returns flattened list
    private fun <T> concatenate(vararg lists: List<T>): List<T> {
        return listOf(*lists).flatten()
    }

    init {
        getFetchData()
    }

}