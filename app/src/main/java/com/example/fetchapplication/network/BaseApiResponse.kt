package com.example.fetchapplication.network

import com.example.fetchapplication.model.data.NetworkResult
import retrofit2.Response

// Helper method that prevents erroneous data from api calls
abstract class BaseApiResponse {
    suspend inline fun <T> safeApiCall(crossinline apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return NetworkResult.Error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }
}