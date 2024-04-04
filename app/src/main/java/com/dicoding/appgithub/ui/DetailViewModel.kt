package com.dicoding.appgithub.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.appgithub.adapter.Result
import com.dicoding.appgithub.api.ApiConfig
import com.dicoding.appgithub.data.ResponsUserGithub
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel: ViewModel() {
    val resultDetailUser = MutableLiveData<Result>()
    val resultFollwerslUser = MutableLiveData<Result>()
    val resultFollwinglUser = MutableLiveData<Result>()

    fun getDetailUser(username:String){
        viewModelScope.launch{
            flow {
                val response = ApiConfig
                    .ApiService
                    .getDetailUserGithub(username)



                emit(response)
            }.onStart {
                resultDetailUser.value = Result.Loading(true)
            }.onCompletion {
                resultDetailUser.value = Result.Loading(false)
            }.catch {
                Log.e("Error",it.message.toString())
                it.printStackTrace()
                resultDetailUser.value = Result.Error(it)
            }.collect{
                resultDetailUser.value = Result.Success(it)
            }

        }
    }


    fun getFollowers(username: String){
        viewModelScope.launch{
            flow {
                val response = ApiConfig
                    .ApiService
                    .getFollowersUserGithub(username)

                emit(response)
            }.onStart {
                resultFollwerslUser.value = Result.Loading(true)
            }.onCompletion {
                resultFollwerslUser.value = Result.Loading(false)
            }.catch {
                Log.e("Error",it.message.toString())
                it.printStackTrace()
                resultFollwerslUser.value = Result.Error(it)
            }.collect{
                resultFollwerslUser.value = Result.Success(it)
            }

        }
    }

    fun getFollowing(username: String){
        viewModelScope.launch{
            flow {
                val response = ApiConfig
                    .ApiService
                    .getFollowingUserGithub(username)

                emit(response)
            }.onStart {
                resultFollwinglUser.value = Result.Loading(true)
            }.onCompletion {
                resultFollwinglUser.value = Result.Loading(false)
            }.catch {
                Log.e("Error",it.message.toString())
                it.printStackTrace()
                resultFollwinglUser.value = Result.Error(it)
            }.collect{
                resultFollwinglUser.value = Result.Success(it)
            }

        }
    }

}