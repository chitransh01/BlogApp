package com.blog.app.viewmodel.repository

import com.blog.app.model.BlogData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET
    fun getBlogDetails(
        @Query("page") page: Int?,
        @Query("limit") limit: Int?
    ): Call<BlogData>
}