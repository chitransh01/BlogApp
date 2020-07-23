package com.blog.app.viewmodel.repository

import com.blog.app.constants.Constants
import com.blog.app.model.BlogData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET(Constants.GET_BLOG_LIST)
    fun getBlogDetails(
        @Query("page") page: Int?,
        @Query("limit") limit: Int?
    ): Call<List<BlogData.BlogDetail>>
}