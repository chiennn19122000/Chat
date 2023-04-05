package com.example.chatgptfree.di

import com.example.chatgptfree.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
//    viewModel { MealViewModel(get()) }
//    viewModel { SearchViewModel(get()) }
//    viewModel { MealDetailViewModel(get(),get()) }
//    viewModel { FavoriteViewModel(get()) }
//    viewModel { ImageZoomViewModel() }
//    viewModel { PersonalViewModel(get()) }
//    viewModel { CartViewModel(get()) }
//    viewModel { RegisterViewModel(get()) }

}
