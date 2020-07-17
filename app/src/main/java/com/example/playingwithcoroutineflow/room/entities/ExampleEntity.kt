package com.example.playingwithcoroutineflow.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "example")
data class ExampleEntity(
    @PrimaryKey
    @SerializedName("Id") var id: String,
    @SerializedName("Image") val image: String?, // https://raw.githubusercontent.com/Softex-Group/task-mobile/master/Ethiopia.jpeg
    @SerializedName("Name") val name: String, // Ethiopia
    @SerializedName("Time") val time: String // 2016-08-08 18:20:20.9566253
)

