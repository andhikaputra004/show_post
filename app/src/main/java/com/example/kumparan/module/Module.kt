package com.example.kumparan.module

import coil.ImageLoader
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.kumparan.BuildConfig
import com.example.kumparan.data.remote.model.PostDTO
import com.example.kumparan.data.remote.service.PostService
import com.example.kumparan.domain.PostRepository
import com.example.kumparan.ui.detailpost.DetailPostViewModel
import com.example.kumparan.ui.detailuser.DetailUserViewModel
import com.example.kumparan.ui.main.MainViewModel
import com.example.kumparan.utils.DrawableLoader
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        Moshi.Builder().build()
    }

    single { UserAgentInterceptor(androidContext()) }
    single {
        OkHttpClient.Builder().apply {
            addInterceptor(ChuckerInterceptor.Builder(androidContext()).build())
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            addInterceptor(get<UserAgentInterceptor>())
            connectTimeout(6000, TimeUnit.MILLISECONDS)
            readTimeout(0, TimeUnit.MILLISECONDS)
        }.build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
        retrofit
    }

    single { get<Retrofit>().create(PostService::class.java) }
    single { PostRepository(get()) }
    single {
        val imageLoader = ImageLoader.Builder(androidContext())
            .okHttpClient(get<OkHttpClient>())
            .build()
        DrawableLoader(androidContext(), imageLoader)
    }
}


val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { (selectedPost: PostDTO) -> DetailPostViewModel(get(), selectedPost) }
    viewModel { (userId: Int) -> DetailUserViewModel(get(), userId) }

}
