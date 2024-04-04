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

class MainViewModel : ViewModel() {

    val resultUser = MutableLiveData<Result>()

    fun getUser(){
        viewModelScope.launch{
                flow {
                    val response = ApiConfig
                        .ApiService
                        .getUserGithub()

                    emit(response)
                }.onStart {
                    resultUser.value = Result.Loading(true)
                }.onCompletion {
                    resultUser.value = Result.Loading(false)
                }.catch {
                    Log.e("Error",it.message.toString())
                    it.printStackTrace()
                    resultUser.value = Result.Error(it)
                }.collect{
                    resultUser.value = Result.Success(it)
            }

        }
    }

    fun getUser(username : String){
        viewModelScope.launch{
                flow {
                    val response = ApiConfig
                        .ApiService
                        .searchUserGithub(mapOf(
                            "q" to username, "per_page" to 15
                        ))

                    emit(response)
                }.onStart {
                    resultUser.value = Result.Loading(true)
                }.onCompletion {
                    resultUser.value = Result.Loading(false)
                }.catch {
                    Log.e("Error",it.message.toString())
                    it.printStackTrace()
                    resultUser.value = Result.Error(it)
                }.collect{
                    resultUser.value = Result.Success(it.items)
            }

        }
    }
}