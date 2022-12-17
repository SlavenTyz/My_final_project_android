package com.example.my_final_project.model.api

import com.example.my_final_project.model.database.Post
import retrofit2.Response
import retrofit2.http.GET


interface PostApi {
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>
}