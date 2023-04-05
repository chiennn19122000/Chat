package com.example.chatgptfree

import android.app.Application
import com.example.chatgptfree.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ChatGptApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ChatGptApplication)
            modules(
                networkModule,
                apiModule,
                databaseModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}