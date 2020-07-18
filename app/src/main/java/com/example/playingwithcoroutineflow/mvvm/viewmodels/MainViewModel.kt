package com.example.playingwithcoroutineflow.mvvm.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import com.example.playingwithcoroutineflow.repos.Repository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.example.playingwithcoroutineflow.mvvm.models.Result
import com.example.playingwithcoroutineflow.room.entities.ExampleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

private const val PAGE_SIZE = 50

@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(private val repo: Repository) : ViewModel(){

    private val isRefreshed = MutableLiveData<Result<Boolean>>()

    //запускаем подгрузку test.json, при создании этого viewModel, если в базе еще нет этих данных
    init {
        getSomeData(isRefresh = false)
    }

    fun getSomeData(isRefresh:Boolean){
        viewModelScope.launch {
            repo.getSomeData(isRefresh)
                .collect {
                    isRefreshed.postValue(it)
                }
        }
    }

    fun isRefreshed() = isRefreshed

    val examplesLiveData = LivePagedListBuilder<Int, ExampleEntity>(repo.getPagedDataSource(), PAGE_SIZE).build()

    fun deleteItem(item:ExampleEntity?){
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteItemFromDataBase(item)
        }
    }
}