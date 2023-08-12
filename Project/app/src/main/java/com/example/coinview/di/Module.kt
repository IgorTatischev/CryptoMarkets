package com.example.coinview.di

import com.example.coinview.data.repository.CoinRepositoryImpl
import com.example.coinview.data.source.CoinsApi
import com.example.coinview.domain.domain.CoinsRepository
import com.example.coinview.domain.use_case.GetCoinsUseCase
import com.example.coinview.domain.use_case.GetMarketsUseCase
import com.example.coinview.presentation.screens.coin_description.CoinDescriptionViewModel
import com.example.coinview.presentation.screens.coins.CoinsListViewModel
import com.example.coinview.presentation.screens.settings.SettingsViewModel
import com.example.coinview.util.ThemeDataStore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val dataModule = module {

    single(named("BASE_URL")) { "https://api.coincap.io/v2/" }

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(get<String>(named("BASE_URL")))
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create<CoinsApi>() }

    single <CoinsRepository>{ CoinRepositoryImpl(api = get()) }


}

val domainModule = module {

    single {
        GetCoinsUseCase(repository = get())
    }

    single {
        GetMarketsUseCase(repository = get())
    }
}

val viewModelModule = module {
    single { ThemeDataStore(context = androidApplication()) }
    viewModelOf(::CoinsListViewModel)
    viewModelOf(::CoinDescriptionViewModel)
    viewModelOf(::SettingsViewModel)
}

