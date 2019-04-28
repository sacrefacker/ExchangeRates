package com.maloshpal.exchangerates.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build.VERSION_CODES
import android.support.annotation.RequiresApi
import android.support.annotation.StringRes
import android.util.Log
import com.maloshpal.exchangerates.app.dagger.AppGraphManager
import com.maloshpal.exchangerates.BuildConfig
import com.maloshpal.exchangerates.R

class ApplicationImpl : Application() {

// MARK: - Methods

    override fun onCreate() {
        super.onCreate()

        Log.i(TAG, "App start, app version: ${BuildConfig.VERSION_NAME}")

//        if (Build.VERSION.SDK_INT >= VERSION_CODES.O) {
//            initializeNotificationCategories()
//        }

        // Init instance variables
        instance = this

        setupDependencyGraph()
    }

// MARK: - Private functions

    private fun setupDependencyGraph() {
        AppGraphManager.initInstance(this)
        AppGraphManager.instance().initBootstrapGraph()
    }

    @RequiresApi(api = VERSION_CODES.O)
    private fun initializeNotificationCategories() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        if (notificationManager != null) {
            val channelTransactions = makeNotificationChannel(R.string.notification_category_other,
                    R.string.notification_category_caption_other, NotificationManager.IMPORTANCE_DEFAULT)

            val channels = listOf(channelTransactions)
            notificationManager.createNotificationChannels(channels)
        }
    }

    @RequiresApi(api = VERSION_CODES.O)
    private fun makeNotificationChannel(@StringRes idRes: Int, @StringRes nameRes: Int, importance: Int): NotificationChannel {
        val id = getString(idRes)
        val name = getString(nameRes)
        return NotificationChannel(id, name, importance)
    }

    companion object {

        private val TAG = Application::class.java.simpleName

        val context: Context
            get() = instance!!.applicationContext

        // Shared instance
        var instance: Application? = null
            private set
    }
}
