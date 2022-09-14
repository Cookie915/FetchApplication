package com.example.fetchapplication.repos

import com.example.fetchapplication.model.data.FetchData
import com.example.fetchapplication.model.data.NetworkResult
import com.example.fetchapplication.network.BaseApiResponse
import com.example.fetchapplication.network.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class FetchRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): BaseApiResponse() {
    suspend fun getFetchData(): Flow<NetworkResult<List<FetchData>>> {
        return flow {
            emit(safeApiCall {
                remoteDataSource.getFetchData()
            })
        }.flowOn(Dispatchers.IO)
    }
}

