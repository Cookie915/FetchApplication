package com.example.fetchapplication.network

import com.example.fetchapplication.model.data.FetchData
import retrofit2.Response
import javax.inject.Inject

// Class that handles getting data from server
class RemoteDataSource @Inject constructor(
    private val fetchApi: FetchApi
) {
    suspend fun getFetchData(): Response<List<FetchData>> =
        fetchApi.getData()
}