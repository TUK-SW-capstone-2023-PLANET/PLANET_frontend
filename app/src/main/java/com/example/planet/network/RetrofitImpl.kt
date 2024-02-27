package com.example.planet.network

import com.example.planet.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val URL = "http://3.37.87.60:8080/"
    private const val NAVER_URL = "https://naveropenapi.apigw.ntruss.com"
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
    fun getRetrofitImpl(): ApiService {
        return Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client())
            .build()
            .create(ApiService::class.java)
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

