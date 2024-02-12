package com.example.myapplication.network

import com.naver.maps.map.BuildConfig
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
    private const val URL = "http://192.168.45.57:8000"
    private const val NAVER_URL = "https://naveropenapi.apigw.ntruss.com"
//    const val NAVER_CLIENT_KEY = BuildConfig.NAVER_CLIENT_KEY
//    const val NAVER_SECRET_KEY = .NAVER_SECRET_KEY

//    val properties = Properties().load(project.rootProject.file("local.properties").inputStream())
    //    properties.load(project.rootProject.file("local.properties").inputStream())
//    val naver_client_id = properties.getProperty("naver_client_id")

    @Singleton
    @Provides
    fun client(): OkHttpClient {
        return  OkHttpClient.Builder()
            .addInterceptor(AppInterceptor())
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
            .addConverterFactory(GsonConverterFactory.create())
            .client(client())
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun getNaverApi(): GeocoderApi {
        return Retrofit.Builder()
            .baseUrl(NAVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client())
            .build()
            .create(GeocoderApi::class.java)
    }

    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
            val newRequest = request().newBuilder()
                .addHeader("X-NCP-APIGW-API-KEY-ID", "(header Value)")
                .addHeader("X-NCP-APIGW-API-KEY", "(header Value)")
                .build()
            proceed(newRequest)
        }
    }
}

