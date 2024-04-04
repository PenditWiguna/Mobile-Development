package com.dicoding.appgithub.api

import com.dicoding.appgithub.data.ResponsDetailUser
import com.dicoding.appgithub.data.ResponsUserGithub
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {



    @JvmSuppressWildcards
    @GET("users")
    suspend fun getUserGithub(): MutableList<ResponsUserGithub.Item>

    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getDetailUserGithub(@Path("username")username:String): ResponsDetailUser

    @JvmSuppressWildcards
    @GET("users/{username}/name")
    suspend fun getNameUserGithub(@Path("username")username:String): ResponsDetailUser

    @JvmSuppressWildcards
    @GET("users/{username}/followers")
    suspend fun getFollowersUserGithub(@Path("username")username:String): MutableList<ResponsUserGithub.Item>

    @JvmSuppressWildcards
    @GET("users/{username}/following")
    suspend fun getFollowingUserGithub(@Path("username")username:String): MutableList<ResponsUserGithub.Item>

    @JvmSuppressWildcards
    @GET("/search/users?")
    suspend fun searchUserGithub(@QueryMap params: Map<String, Any>): ResponsUserGithub

}