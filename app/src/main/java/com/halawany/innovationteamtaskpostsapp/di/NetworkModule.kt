package com.halawany.innovationteamtaskpostsapp.di

import com.halawany.innovationteamtaskpostsapp.core.network.ConnectivityObserver
import com.halawany.innovationteamtaskpostsapp.core.network.ConnectivityObserverImpl
import com.halawany.innovationteamtaskpostsapp.core.network.NetworkMonitor
import com.halawany.innovationteamtaskpostsapp.core.network.NetworkMonitorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideNetworkMonitor(impl: NetworkMonitorImpl): NetworkMonitor = impl

    @Provides
    @Singleton
    fun provideConnectivityObserver(impl: ConnectivityObserverImpl): ConnectivityObserver = impl

}
