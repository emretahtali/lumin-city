package com.example.huckathon.di

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.SavedStateHandle
import com.example.huckathon.presentation.screens.mapScreen.viewmodel.MapScreenViewModel
import com.example.huckathon.presentation.screens.paymentscreen.viewmodel.PaymentScreenViewModel
import com.example.huckathon.presentation.screens.profile.viewmodel.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    //  Network

    // Repository

    //viewmodel
    viewModel{ ProfileViewModel() }
    viewModel{ MapScreenViewModel()}
    viewModel{ PaymentScreenViewModel(get())}
}