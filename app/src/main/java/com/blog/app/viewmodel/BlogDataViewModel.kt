package com.blog.app.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blog.app.BlogApplication
import com.blog.app.model.BlogData
import com.blog.app.viewmodel.repository.BlogRepository

class BlogDataViewModel(application: BlogApplication, page: Int?, limit: Int?) :
    AndroidViewModel(application) {
    private var mBlogObservable: LiveData<List<BlogData.BlogDetail>?>? = null

    init {
        mBlogObservable = BlogRepository.instance?.getBlogListData(page, limit)
    }

    fun getObservableBlogModel(): LiveData<List<BlogData.BlogDetail>?>? {
        return mBlogObservable
    }

    class Factory(
        private val application: BlogApplication,
        private val page: Int,
        private val limit: Int
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BlogDataViewModel(application, page, limit) as T
        }
    }
}