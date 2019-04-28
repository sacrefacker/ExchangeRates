package com.maloshpal.exchangerates.ui.activity

import android.app.Activity
import android.content.Intent
import android.view.View
import com.maloshpal.exchangerates.R
import org.androidannotations.annotations.AfterInject
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.ViewById

@EActivity
open class InitialActivity: Activity() {

// MARK: - Properties

    // Hack to make sure ClassUtils.getGeneratedName correctly finds InitialActivity_.class
    @ViewById(R.id.main_container)
    internal lateinit var dummyView: View

// MARK: - Methods

    @AfterInject
    fun beforeInit() {

        if (!isTaskRoot) {
            val intentAction = intent.action
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER)
                    && intentAction != null
                    && intentAction == Intent.ACTION_MAIN) {
                finish()
                return
            }
        }

        startActivity(MainActivity.newIntent(this))

        finish()
    }
}