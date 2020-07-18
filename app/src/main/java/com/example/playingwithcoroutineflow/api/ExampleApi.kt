package com.example.playingwithcoroutineflow.api

import com.example.playingwithcoroutineflow.room.entities.ExampleEntity
import retrofit2.http.GET

interface ExampleApi {
    @GET("/test.json")
    suspend fun getSomData():List<ExampleEntity>
}