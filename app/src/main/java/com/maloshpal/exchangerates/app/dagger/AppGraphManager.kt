package com.maloshpal.exchangerates.app.dagger

import android.annotation.SuppressLint
import android.content.Context

interface IAppGraphManager {
    fun initBootstrapGraph()
}

class AppGraphManager private constructor(
        private val appContext: Context
) : IAppGraphManager {

// MARK: - Properties

//    private val prefDb: IDatabase = SharedPreferenceDatabase(PreferenceManager.getDefaultSharedPreferences(this.appContext))

// MARK: - Public functions

    override fun initBootstrapGraph() {

//        val bootstrapComponent = DaggerBootstrapComponent.builder()
//                .bootstrapModule(BootstrapModule(appContext)
//                .managerModule(ManagerModule())
//                .build()
//
//        InjectionHolder.instance().bootstrapComponent = bootstrapComponent
    }

// MARK: - Companion

    companion object {
        private val TAG = AppGraphManager::class.java.simpleName

        @SuppressLint("StaticFieldLeak")
        private lateinit var INSTANCE: AppGraphManager

        fun instance() = INSTANCE

        fun initInstance(appContext: Context) {
            INSTANCE = AppGraphManager(appContext)
        }
    }
}