package com.example.chatgptfree.di

import com.example.chatgptfree.data.repository.CompletionRepository
import com.example.chatgptfree.data.repository.CompletionRepositoryImpl
import com.example.chatgptfree.data.source.CompletionDataSource
import com.example.chatgptfree.data.source.remote.CompletionRemoteDataSource
import org.koin.dsl.module

val repositoryModule = module {
    single<CompletionDataSource> { CompletionRemoteDataSource(get()) }
    single<CompletionRepository> { CompletionRepositoryImpl(get()) }
}
