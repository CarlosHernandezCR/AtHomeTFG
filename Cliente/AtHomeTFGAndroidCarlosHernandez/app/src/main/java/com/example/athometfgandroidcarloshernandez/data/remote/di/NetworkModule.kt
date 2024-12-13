package com.example.athometfgandroidcarloshernandez.data.remote.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.athometfgandroidcarloshernandez.common.Constantes
import com.example.athometfgandroidcarloshernandez.data.remote.apiServices.CasaService
import com.example.athometfgandroidcarloshernandez.data.remote.apiServices.EventosService
import com.example.athometfgandroidcarloshernandez.data.remote.apiServices.InmueblesService
import com.example.athometfgandroidcarloshernandez.data.remote.apiServices.UsuarioService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constantes.DATA_STORE_NAME)

    @Singleton
    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Singleton
    @Provides
    fun provideHttpClient(
        authenticatorInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(Constantes.TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(Constantes.TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authenticatorInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constantes.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideTokenManager(
        @ApplicationContext context: Context,
    ): TokenManager = TokenManager(context)

    @Singleton
    @Provides
    fun provideServiceInterceptor(tokenManager: TokenManager): AuthInterceptor =
        AuthInterceptor(tokenManager)

    @Singleton
    @Provides
    fun provideUsuarioService(retrofit: Retrofit): UsuarioService =
        retrofit.create(UsuarioService::class.java)

    @Singleton
    @Provides
    fun provideEventosService(retrofit: Retrofit): EventosService =
        retrofit.create(EventosService::class.java)

    @Singleton
    @Provides
    fun provideInmueblesService(retrofit: Retrofit): InmueblesService =
        retrofit.create(InmueblesService::class.java)
    @Singleton
    @Provides
    fun provideCasaService(retrofit: Retrofit): CasaService =
        retrofit.create(CasaService::class.java)

    @Singleton
    @Provides
    fun provideDataStore(context: Context): DataStore<Preferences> =
        context.dataStore

}