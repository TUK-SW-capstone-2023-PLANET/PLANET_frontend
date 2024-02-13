package com.example.myapplication.network

import com.example.myapplication.BuildConfig
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
    private const val URL = "http://192.168.45.57:8000"
    private const val NAVER_URL = "https://naveropenapi.apigw.ntruss.com"
//    val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()

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
    fun naverHeader(): OkHttpClient {
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
        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder()
            .baseUrl(NAVER_URL)
//            .addConverterFactory(TikXmlConverterFactory.create(parser))
            .addConverterFactory(GsonConverterFactory.create())
            .client(naverHeader())
            .build()
            .create(GeocoderApi::class.java)
    }

    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
            val newRequest = request().newBuilder()
                .addHeader("X-NCP-APIGW-API-KEY-ID", "${BuildConfig.NAVER_CLIENT_KEY}")
                .addHeader("X-NCP-APIGW-API-KEY", "${BuildConfig.NAVER_SECRET_KEY}")
                .build()
            proceed(newRequest)
        }
    }
}

