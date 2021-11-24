package com.example.astronomyexercise.di

import android.content.Context
import androidx.room.Room
import com.example.astronomyexercise.data.models.NasaApi
import com.example.astronomyexercise.db.NasaDao
import com.example.astronomyexercise.db.NasaDatabase
import com.example.astronomyexercise.main.DefaultMainRepository
import com.example.astronomyexercise.main.MainRepository
import com.example.astronomyexercise.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.nasa.gov/planetary/"


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideNasaApi(): NasaApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NasaApi::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(api: NasaApi, dao: NasaDao): MainRepository =
        DefaultMainRepository(api, dao)

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        NasaDatabase::class.java,
        "images"
    ).build()

    @Singleton
    @Provides
    fun provideYourDao(db: NasaDatabase) = db.getNasaDao()
}