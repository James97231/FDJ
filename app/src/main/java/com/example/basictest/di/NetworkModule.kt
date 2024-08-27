package com.example.basictest.di

import com.example.basictest.data.remote.SportsApi
import com.example.basictest.data.repository.SportsRepositoryImpl
import com.example.basictest.domain.repository.SportsRepository
import com.example.basictest.domain.usecase.GetAllLeaguesUseCase
import com.example.basictest.domain.usecase.GetTeamsByLeagueUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://www.thesportsdb.com/api/v1/json/50130162/")
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideSportsApi(retrofit: Retrofit): SportsApi = retrofit.create(SportsApi::class.java)

    @Provides
    @Singleton
    fun provideSportsRepository(api: SportsApi): SportsRepository = SportsRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideGetAllLeaguesUseCase(repository: SportsRepository): GetAllLeaguesUseCase = GetAllLeaguesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetTeamsByLeagueUseCase(repository: SportsRepository): GetTeamsByLeagueUseCase = GetTeamsByLeagueUseCase(repository)
}
