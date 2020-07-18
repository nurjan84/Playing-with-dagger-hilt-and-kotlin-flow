package com.example.playingwithcoroutineflow.repos

import com.example.playingwithcoroutineflow.api.ApiCaller
import com.example.playingwithcoroutineflow.api.ExampleApi
import com.example.playingwithcoroutineflow.room.dao.ExampleDao
import com.example.playingwithcoroutineflow.room.entities.ExampleEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class Repository @Inject constructor(
                                    private val api: ExampleApi,
                                    private val exampleDao: ExampleDao) : ApiCaller(){

    fun getSomeData(isRefresh:Boolean) = request{
        val count = exampleDao.getCountOfRecords()
        if(count == 0 || isRefresh){
            api.getSomData().forEach {
                exampleDao.insert(it)
            }
            true
        }else{
            false
        }
    }

    fun getPagedDataSource() = exampleDao.getData()

    fun deleteItemFromDataBase(item: ExampleEntity?){
        item?.let {
            exampleDao.deleteById(it.id)
        }
    }

}