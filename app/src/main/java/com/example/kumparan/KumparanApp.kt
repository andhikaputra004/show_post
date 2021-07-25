package com.example.kumparan

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import com.example.kumparan.module.networkModule
import com.example.kumparan.module.viewModelModule
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class KumparanApp : Application() {

    lateinit var koinApplication: KoinApplication

    override fun onCreate() {
        super.onCreate()

        koinApplication = startKoin {
            androidContext(this@KumparanApp)
            modules(
                networkModule, viewModelModule
            )
        }

        val imageLoader = ImageLoader.Builder(this)
            .okHttpClient {
                koinApplication.koin.get<OkHttpClient>()
                    .newBuilder()
                    .cache(CoilUtils.createDefaultCache(this))
                    .build()
            }
            .build()
        Coil.setImageLoader(imageLoader)
    }
}