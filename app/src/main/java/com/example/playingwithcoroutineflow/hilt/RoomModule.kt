package com.example.playingwithcoroutineflow.hilt

import android.content.Context
import androidx.room.Room
import com.example.playingwithcoroutineflow.room.dao.ExampleDao
import com.example.playingwithcoroutineflow.room.db.DataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun bindRoomDatabase(@ApplicationContext context: Context): DataBase {
        return Room.databaseBuilder(context,
            DataBase::class.java, "example.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideExampleDao(db: DataBase): ExampleDao {
        return db.exampleDao()
    }

}