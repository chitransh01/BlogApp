package com.blog.app.viewmodel.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blog.app.constants.Constants
import com.blog.app.model.BlogData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BlogRepository private constructor() {
    companion object {
        private val TAG = BlogRepository::class.java.simpleName
        private var mRepository: BlogRepository? = null

        @get:Synchronized
        val instance: BlogRepository?
            get() {
                if (mRepository == null) {
                    mRepository = BlogRepository()
                }
                return mRepository
            }
    }

    private var mAPIService: APIService? = null

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mAPIService = retrofit.create(APIService::class.java)
    }

    fun getBlogListData(page: Int?, limit: Int?): LiveData<BlogData?> {
        val data: MutableLiveData<BlogData?> = MutableLiveData()
        mAPIService?.getBlogDetails(page, limit)?.enqueue(object : Callback<BlogData?> {
            override fun onResponse(call: Call<BlogData?>?, response: Response<BlogData?>?) {
                if (response?.code() == Constants.API_SUCCESS) {
                    data.value = response.body()
                } else {
                    data.value = null
                }
            }

            override fun onFailure(call: Call<BlogData?>?, t: Throwable?) {
                data.value = null
            }
        })
        return data
    }
}