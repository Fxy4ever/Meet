package com.mredrock.cyxbs.summer.utils.network

import com.mredrock.cyxbs.summer.BuildConfig
import com.mredrock.cyxbs.summer.config.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiGenerator{
    private const val DEFAULT_TIME_OUT = 20

    private var retrofit: Retrofit
    private var okHttpClient: OkHttpClient

    init{
        okHttpClient = initOkHttp(OkHttpClient.Builder())
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private fun initOkHttp(builder: OkHttpClient.Builder):OkHttpClient{
        if(BuildConfig.DEBUG){
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }
        builder.connectTimeout(DEFAULT_TIME_OUT.toLong(), TimeUnit.SECONDS)
        return builder.build()
    }

    fun <T> getApiService(clazz: Class<T>) = retrofit.create(clazz)


}