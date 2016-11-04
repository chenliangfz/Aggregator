package com.aggregator.card.core.inject.module;

import android.content.Context;

import com.aggregator.card.source.network.ApiService;
import com.aggregator.card.source.network.CacheInterceptor;
import com.aggregator.card.source.network.HttpLogInterceptor;
import com.aggregator.card.source.network.ItemTypeAdapterFactory;
import com.aggregator.card.source.network.RequestInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @Provides
    HttpLogInterceptor provideHttpLogInterceptor() {
        HttpLogInterceptor logInterceptor = new HttpLogInterceptor();
        logInterceptor.setLevel(HttpLogInterceptor.Level.BODY);
        return logInterceptor;
    }

    @Provides
    RequestInterceptor provideRequestInterceptor(Context context) {
        return new RequestInterceptor(context);
    }

    @Provides
    CacheInterceptor provideCacheInterceptor(Context context) {
        return new CacheInterceptor(context);
    }

    @Provides
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .create();
    }

    @Provides
    Cache provideCache(Context context) {
        File cacheFile = new File(context.getCacheDir(), "Http Cache");
        long cacheSize = 10 * 1024 * 1024;
        return new Cache(cacheFile, cacheSize);
    }

    @Provides
    OkHttpClient provideOkHttpClient(Cache cache, HttpLogInterceptor httpLogInterceptor, RequestInterceptor
            requestInterceptor, CacheInterceptor cacheInterceptor) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(requestInterceptor)
                .addInterceptor(httpLogInterceptor)
                .addNetworkInterceptor(cacheInterceptor)
                .addInterceptor(cacheInterceptor)
                .build();
    }

    @Provides
    Converter.Factory provideConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    CallAdapter.Factory provideCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    @Provides
    Retrofit provideRetrofit(Converter.Factory converterFactory, CallAdapter.Factory callAdapterFactory, OkHttpClient
            okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    ApiService provideApiService(Retrofit retrofit) {
        ApiService apiService = retrofit.create(ApiService.class);
        return apiService;
    }
}
