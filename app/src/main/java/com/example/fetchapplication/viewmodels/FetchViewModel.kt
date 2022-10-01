package com.example.fetchapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchapplication.model.data.FetchData
import com.example.fetchapplication.model.data.NetworkResult
import com.example.fetchapplication.repos.FetchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FetchViewModel @Inject constructor(
    private val fetchRepository: FetchRepository
): ViewModel() {
    private val _fetchData: MutableStateFlow<NetworkResult<List<FetchData>>> = MutableStateFlow(NetworkResult.Loading())
    val fetchData = _fetchData.asStateFlow()

    private val _listMap: MutableStateFlow<MutableMap<Int, MutableList<FetchData>>> = MutableStateFlow(mutableMapOf())
    val listMap = _listMap.asStateFlow()

    //  Attempts to fetch data from server, and update state appropriately
    fun getFetchData() = viewModelScope.launch {
        _fetchData.value = NetworkResult.Loading()
        fetchRepository.getFetchData().collect { result ->
            //  on success filter data and update individual lists
            if (result is NetworkResult.Success) {
                val filteredLists = result.data!!.filter {
                    !it.name.isNullOrEmpty()
                }
                val map: MutableMap<Int, MutableList<FetchData>> = mutableMapOf()
                filteredLists.forEach { fetchDataItem ->
                    val mapEntry = map.getOrPut(fetchDataItem.listId){
                        mutableListOf(fetchDataItem)
                    }
                    mapEntry.add(fetchDataItem)
                }
                map.forEach {
                    it.value.sortBy { fetchData ->
                        fetchData.id
                    }
                }
                _listMap.value = map
            }
            _fetchData.value = result
        }
    }
    
    init {
        getFetchData()
    }

}