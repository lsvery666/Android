package com.example.retrofittest

import retrofit2.Call
import retrofit2.http.GET

interface BaiDuService {
    @GET("/")
    fun getData(): Call<String>
}