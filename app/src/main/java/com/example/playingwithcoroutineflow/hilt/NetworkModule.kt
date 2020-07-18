package com.example.playingwithcoroutineflow.hilt

import android.content.Context
import com.example.playingwithcoroutineflow.BuildConfig
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.*
import okhttp3.internal.platform.Platform
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    private fun loadPageFromAssets(context: Context, file:String):String?{
        var tContents: String? = ""
        try {
            val stream: InputStream = context.assets.open(file)
            val size: Int = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            tContents = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return tContents
    }

    //интерцептор который подгружает данные из локального файла,
    //если endpoint равен /test.json
    @Singleton
    @Provides
    fun interceptor(@ApplicationContext context: Context):Interceptor{
        return Interceptor { chain ->
            val request = chain.request()
            if(request.url().encodedPath() =="/test.json"){
                val resp = loadPageFromAssets(context, "test.json")
                Response.Builder()
                    .code(200)
                    .message(resp?:"")
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .body(ResponseBody.create(MediaType.parse("application/json"), resp?:""))
                    .addHeader("content-type", "application/json")
                    .build()
            }else{
                chain.proceed(request)
            }
        }
    }

    // интерецептор для показа логов
    @Singleton
    @Provides
    fun loggingInterceptor(): LoggingInterceptor {
        return LoggingInterceptor.Builder()
            .loggable(BuildConfig.SHOW_LOG)
            .setLevel(Level.BODY)
            .log(Platform.INFO)
            .request("Request")
            .response("Response")
            .build()
    }

    // создаем http клиент
    @Singleton
    @Provides
    fun okHttpClient(interceptor: Interceptor, httpLoggingInterceptor: LoggingInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(interceptor)
            .build()
    }


}

