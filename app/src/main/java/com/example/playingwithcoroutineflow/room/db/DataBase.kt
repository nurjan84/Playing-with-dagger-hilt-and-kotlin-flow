package com.example.playingwithcoroutineflow.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playingwithcoroutineflow.room.dao.ExampleDao
import com.example.playingwithcoroutineflow.room.entities.ExampleEntity

@Database(entities = [(ExampleEntity::class)], version = 1, exportSchema = false)
abstract class DataBase: RoomDatabase(){
    abstract fun exampleDao(): ExampleDao
}