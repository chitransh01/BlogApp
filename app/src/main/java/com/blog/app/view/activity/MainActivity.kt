package com.blog.app.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.blog.app.BlogApplication
import com.blog.app.R
import com.blog.app.model.BlogData
import com.blog.app.utils.NetworkUtil
import com.blog.app.view.adapter.ArticleRecyclerViewAdapter
import com.blog.app.view.callbacks.OnOkButtonClickListener
import com.blog.app.view.dialog.DialogUtils
import com.blog.app.view.listener.PaginationScrollListener
import com.blog.app.viewmodel.BlogDataViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private var mIsLastPage: Boolean = false
    private var mIsLoading: Boolean = false
    private var mPage = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {
            setSupportActionBar(toolbar)
            if (NetworkUtil.isConnectedToWifi(this)) {
                getArticleDataFromServer(mPage)
            } else {
                showAlertMessage()
            }
        } catch (e: Exception) {
            Log.e(TAG, "onCreate: " + e.message)
        }
    }

    private fun getArticleDataFromServer(page: Int) {
        try {
            progressBar.visibility = View.VISIBLE
            val factory = BlogDataViewModel.Factory(BlogApplication.getInstance(this), page, 10)
            val blogViewModel =
                ViewModelProviders.of(this, factory).get(BlogDataViewModel::class.java)
            observeViewModel(blogViewModel)
        } catch (e: Exception) {
            Log.e(TAG, "getArticleDataFromServer: " + e.message)
        }
    }

    private fun observeViewModel(blogViewModel: BlogDataViewModel) {
        try {
            blogViewModel.getObservableBlogModel()?.observe(this, Observer { blogList ->
                if (blogList != null) {
                    progressBar.visibility = View.GONE
                    initRecyclerView(blogList)
                } else {
                    Toast.makeText(this, getString(R.string.msg_api_error), Toast.LENGTH_SHORT)
                        .show()
                }
            })
        } catch (e: Exception) {
            Log.e(TAG, "observeViewModel: " + e.message)
        }
    }

    private fun initRecyclerView(blogList: List<BlogData.BlogDetail>) {
        try {
            val layoutManager = LinearLayoutManager(this)
            val adapter = ArticleRecyclerViewAdapter(this, blogList)
            if (mPage == 1) {
                rvArticles.layoutManager = layoutManager
                rvArticles.itemAnimator = DefaultItemAnimator()
                rvArticles.adapter = adapter
            } else {
                mIsLoading = false
                adapter.addArticles(blogList)
            }
            rvArticles.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
                override fun isLastPage(): Boolean {
                    return mIsLastPage
                }

                override fun isLoading(): Boolean {
                    return mIsLoading
                }

                override fun loadMoreItems() {
                    mIsLoading = true
                    mPage++
                    getArticleDataFromServer(mPage)
                }
            })
        } catch (e: Exception) {
            Log.e(TAG, "initRecyclerView: " + e.message)
        }
    }

    /**
     * This method is used to show alert message for no wifi connectivity
     */
    private fun showAlertMessage() {
        try {
            DialogUtils.showAlertDialog(this,
                getString(R.string.title_no_wifi_network),
                getString(R.string.msg_no_wifi_connectivity),
                getString(R.string.btn_ok),
                object : OnOkButtonClickListener {
                    override fun onOkButtonCLicked() {
                        finish()
                    }
                }
            )
        } catch (e: Exception) {
            Log.e(TAG, "setUpGClient: " + e.message)
        }
    }
}