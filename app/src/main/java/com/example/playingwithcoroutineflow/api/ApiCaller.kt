package com.example.playingwithcoroutineflow.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.example.playingwithcoroutineflow.mvvm.models.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi

open class ApiCaller {
    @ExperimentalCoroutinesApi
    fun <T>request(response: suspend () -> T) = flow {
        emit(Result.loading<T>())
        emit(Result.success<T>(response.invoke()))
    } .flowOn(Dispatchers.IO) .catch { emit(Result.error(it)) }
}