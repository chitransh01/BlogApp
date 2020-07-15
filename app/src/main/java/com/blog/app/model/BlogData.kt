package com.blog.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BlogData {
    var blogList: List<BlogDetail>? = null

    class BlogDetail {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("createdAt")
        @Expose
        var createdAt: String? = null

        @SerializedName("content")
        @Expose
        var content: String? = null

        @SerializedName("comments")
        @Expose
        var comments: Int? = null

        @SerializedName("likes")
        @Expose
        var likes: Int? = null

        @SerializedName("media")
        @Expose
        var media: List<Media>? = null

        @SerializedName("user")
        @Expose
        var user: List<User>? = null

        class Media {
            @SerializedName("id")
            @Expose
            var id: String? = null

            @SerializedName("blogId")
            @Expose
            var blogId: String? = null

            @SerializedName("createdAt")
            @Expose
            var createdAt: String? = null

            @SerializedName("image")
            @Expose
            var image: String? = null

            @SerializedName("title")
            @Expose
            var title: String? = null

            @SerializedName("url")
            @Expose
            var url: String? = null
        }

        class User {
            @SerializedName("id")
            @Expose
            var id: String? = null

            @SerializedName("blogId")
            @Expose
            var blogId: String? = null

            @SerializedName("createdAt")
            @Expose
            var createdAt: String? = null

            @SerializedName("name")
            @Expose
            var name: String? = null

            @SerializedName("avatar")
            @Expose
            var avatar: String? = null

            @SerializedName("lastname")
            @Expose
            var lastname: String? = null

            @SerializedName("city")
            @Expose
            var city: String? = null

            @SerializedName("designation")
            @Expose
            var designation: String? = null

            @SerializedName("about")
            @Expose
            var about: String? = null
        }
    }
}