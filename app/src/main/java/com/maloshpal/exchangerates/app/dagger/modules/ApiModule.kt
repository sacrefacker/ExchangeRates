package com.maloshpal.exchangerates.app.dagger.modules

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.maloshpal.exchangerates.app.dagger.scopes.BootScope
import com.maloshpal.exchangerates.app.manager.ApiService
import com.maloshpal.exchangerates.app.manager.EndpointManager
import com.maloshpal.exchangerates.app.manager.IEndpointManager
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    @BootScope
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }

    @Provides
    fun provideEndpointManager(): IEndpointManager {
        return EndpointManager
    }

    @BootScope
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @BootScope
    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
    }

    @BootScope
    @Provides
    fun provideApiService(gson: Gson, endpointManager: IEndpointManager, client: OkHttpClient): ApiService {
        return Retrofit.Builder()
                .baseUrl(endpointManager.endpoint)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
    }
}