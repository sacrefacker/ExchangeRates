package com.maloshpal.exchangerates.app.dagger.modules

import android.content.Context
import com.maloshpal.exchangerates.app.dagger.IAppGraphManager
import com.maloshpal.exchangerates.app.dagger.scopes.BootScope
import dagger.Module
import dagger.Provides

@Module
class BootstrapModule(
        private val appContext: Context,
        private val appGraphManager: IAppGraphManager
) {

    @Provides
    @BootScope
    fun provideAppContext(): Context {
        return appContext
    }

    @Provides
    @BootScope
    internal fun provideAppGraphManager(): IAppGraphManager {
        return appGraphManager
    }

//    @Provides
//    @BootScope
//    internal fun provideDatabase(): IDatabase {
//        return this.prefDb
//    }
}