package com.blog.app.view.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blog.app.R
import com.blog.app.model.BlogData
import com.blog.app.utils.getTimeDiff
import com.blog.app.utils.getValue
import com.bumptech.glide.Glide

class ArticleRecyclerViewAdapter(
    private val context: Context,
    private var blogList: List<BlogData.BlogDetail>?
) : RecyclerView.Adapter<ArticleRecyclerViewAdapter.ArticleViewHolder>() {
    private val mInflater = LayoutInflater.from(context)

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivProfile: ImageView? = null
        var ivArticle: ImageView? = null
        var tvUsername: TextView? = null
        var tvDesignation: TextView? = null
        var tvTime: TextView? = null
        var tvArticleContent: TextView? = null
        var tvArticleTitle: TextView? = null
        var tvArticleUrl: TextView? = null
        var tvLikes: TextView? = null
        var tvComments: TextView? = null

        init {
            ivProfile = view.findViewById(R.id.ivProfile)
            ivArticle = view.findViewById(R.id.ivArticle)
            tvUsername = view.findViewById(R.id.tvUsername)
            tvDesignation = view.findViewById(R.id.tvDesignation)
            tvTime = view.findViewById(R.id.tvTime)
            tvArticleContent = view.findViewById(R.id.tvArticleContent)
            tvArticleTitle = view.findViewById(R.id.tvArticleTitle)
            tvArticleUrl = view.findViewById(R.id.tvArticleUrl)
            tvLikes = view.findViewById(R.id.tvLikes)
            tvComments = view.findViewById(R.id.tvComments)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = mInflater.inflate(R.layout.layout_articles_child, parent, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        val size = blogList?.size
        if (size != null) {
            return if (size > 0) {
                size
            } else {
                0
            }
        }
        return 0
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val blogDetail = blogList?.get(position)
        holder.ivProfile?.let {
            Glide.with(context).load(blogDetail?.user?.get(0)?.avatar).into(it)
        }
        val name = blogDetail?.user?.get(0)?.name + " " + blogDetail?.user?.get(0)?.lastname
        holder.tvUsername?.text = name
        holder.tvDesignation?.text = blogDetail?.user?.get(0)?.designation
        holder.tvTime?.text = blogDetail?.createdAt?.getTimeDiff()
        holder.ivArticle?.let {
            Glide.with(context).load(blogDetail?.media?.get(0)?.image).into(it)
        }
        holder.tvArticleContent?.text = blogDetail?.content
        holder.tvArticleTitle?.text = blogDetail?.media?.get(0)?.title
        holder.tvArticleUrl?.text = Html.fromHtml(blogDetail?.media?.get(0)?.url)
        val likes = blogDetail?.likes?.getValue() + context.getString(R.string.lbl_likes)
        holder.tvLikes?.text = likes
        val comments = blogDetail?.comments?.getValue() + context.getString(R.string.lbl_comments)
        holder.tvComments?.text = comments
    }

    fun addArticles(blogList: List<BlogData.BlogDetail>?) {
        val size = this.blogList?.size
        blogList?.let { (this.blogList as ArrayList<BlogData.BlogDetail>?)?.addAll(it) }
        val sizeNew = this.blogList?.size
        size?.let { oSize -> sizeNew?.let { nSize -> notifyItemRangeChanged(oSize, nSize) } }
    }
}