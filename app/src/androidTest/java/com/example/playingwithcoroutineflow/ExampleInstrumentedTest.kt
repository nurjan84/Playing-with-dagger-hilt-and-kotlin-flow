package com.example.playingwithcoroutineflow

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.playingwithcoroutineflow.repos.Repository
import com.example.playingwithcoroutineflow.room.dao.ExampleDao
import com.example.playingwithcoroutineflow.room.db.DataBase
import com.example.playingwithcoroutineflow.room.entities.ExampleEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.io.IOException
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var exampleDao: ExampleDao
    private lateinit var db: DataBase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DataBase::class.java).build()
        exampleDao = db.exampleDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun countTest() {
        val example =  ExampleEntity(id = "id", image = "image", name = "name", time = "time")
        exampleDao.insert(example)
        val count = exampleDao.getCountOfRecords()
        assertThat(count, equalTo(1))
    }
}

@ExperimentalCoroutinesApi
@HiltAndroidTest
class RepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repo: Repository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun repoTest() {
        var isRefreshed:Boolean? = null
        runBlocking {
            launch {
                repo.getSomeData(true)
                    .collect {
                        isRefreshed = it.data
                    }
            }
        }
        assertThat(isRefreshed, equalTo(true))
    }
}