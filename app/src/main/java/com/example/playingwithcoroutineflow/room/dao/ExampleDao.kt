package com.example.playingwithcoroutineflow.room.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playingwithcoroutineflow.room.entities.ExampleEntity

@Dao
interface ExampleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(o: ExampleEntity)

    @Query("SELECT COUNT(id) FROM example")
    fun getCountOfRecords():Int

    @Query("SELECT * FROM example")
    fun getData(): DataSource.Factory<Int, ExampleEntity>

    @Query("DELETE FROM example WHERE id=:id")
    fun deleteById(id:String)

    @Query("DELETE FROM example")
    fun deleteAll()
}