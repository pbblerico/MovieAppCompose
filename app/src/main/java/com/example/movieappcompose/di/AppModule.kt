package com.example.movieappcompose.di

import com.example.movieappcompose.movieList.repository.MoviePagingSource
import com.example.movieappcompose.movieList.repository.MovieRepository
import com.example.movieappcompose.movieList.repository.MovieRepositoryImpl
import com.example.movieappcompose.movieList.viewModel.MovieViewModel
import com.example.movieappcompose.retrofit.ApiService
import com.example.movieappcompose.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single { interceptor() }
    single { okhttpClient(get()) }
    single { retrofit(get()) }
    single { apiService(get()) }
    factory { MoviePagingSource(get()) }
    single<MovieRepository> { return@single MovieRepositoryImpl(get(), get()) }


    viewModel { MovieViewModel(get()) }
}

private fun interceptor(): Interceptor =
    Interceptor { chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", Constants.API_KEY)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return@Interceptor chain.proceed(request)
    }


private fun okhttpClient(requestInterceptor: Interceptor): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)

    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(requestInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()
}

private fun retrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

private fun apiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)