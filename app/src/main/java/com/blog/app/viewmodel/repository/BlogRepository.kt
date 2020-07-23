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

    fun getBlogListData(page: Int?, limit: Int?): LiveData<List<BlogData.BlogDetail>?> {
        val data: MutableLiveData<List<BlogData.BlogDetail>?> = MutableLiveData()
        mAPIService?.getBlogDetails(page, limit)?.enqueue(object : Callback<List<BlogData.BlogDetail>?> {
            override fun onResponse(call: Call<List<BlogData.BlogDetail>?>?, response: Response<List<BlogData.BlogDetail>?>?) {
                if (response?.code() == Constants.API_SUCCESS) {
                    data.value = response.body()
                } else {
                    data.value = null
                }
            }

            override fun onFailure(call: Call<List<BlogData.BlogDetail>?>?, t: Throwable?) {
                data.value = null
            }
        })
        return data
    }
}