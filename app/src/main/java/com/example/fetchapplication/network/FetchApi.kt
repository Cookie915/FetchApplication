package com.example.fetchapplication.network

import com.example.fetchapplication.model.data.FetchData
import org.intellij.lang.annotations.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
// Api calls
interface FetchApi {
    @GET("hiring.json")
    suspend fun getData(): Response<List<FetchData>>
}