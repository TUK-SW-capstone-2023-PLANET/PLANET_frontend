package com.example.planet.di.module

import com.example.planet.data.remote.api.ai.AiApi
import com.example.planet.data.remote.api.spring.MainApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val MAIN_URL = "http://3.37.87.60:8080/"
    private const val AI_URL = "http://172.20.10.5:8081/"

    // 스웨거 주소: http://3.37.87.60:8080/swagger-ui/index.html
    val gson : Gson = GsonBuilder()
        .setLenient()
        .create()
    @Singleton
    @Provides
    fun client(): OkHttpClient {
        return  OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun getMainApi(): MainApi {
        return Retrofit.Builder()
            .baseUrl(MAIN_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client())
            .build()
            .create(MainApi::class.java)
    }

    @Singleton
    @Provides
    fun getAiApi(): AiApi {
        return Retrofit.Builder()
            .baseUrl(AI_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client())
            .build()
            .create(AiApi::class.java)
    }

//    class AppInterceptor : Interceptor {
//        @Throws(IOException::class)
//        override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
//            val newRequest = request().newBuilder()
//                .addHeader("X-NCP-APIGW-API-KEY-ID", "${BuildConfig.NAVER_CLIENT_KEY}")
//                .addHeader("X-NCP-APIGW-API-KEY", "${BuildConfig.NAVER_SECRET_KEY}")
//                .build()
//            proceed(newRequest)
//        }
//    }
}

