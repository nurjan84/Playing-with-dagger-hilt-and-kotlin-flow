package com.example.playingwithcoroutineflow.api

import com.example.playingwithcoroutineflow.room.entities.ExampleEntity
import retrofit2.http.GET
import retrofit2.http.Url

interface ExampleApi {
    @GET
    suspend fun getSomData(@Url url:String):List<ExampleEntity>
}