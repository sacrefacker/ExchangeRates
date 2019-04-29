package com.maloshpal.exchangerates.ui.activity

import android.content.Context
import android.content.Intent
import com.maloshpal.exchangerates.ui.util.ClassUtils
import com.maloshpal.exchangerates.R
import com.maloshpal.exchangerates.ui.activity.base.BaseAppActivity

import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_main)
open class MainActivity : BaseAppActivity() {

// MARK: - Companion

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, ClassUtils.getGeneratedClass(MainActivity::class.java))
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            return intent
        }

        private val TAG = MainActivity::class.java.simpleName
    }
}