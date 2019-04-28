package com.maloshpal.exchangerates.ui.util

import android.content.Intent
import android.os.Bundle

object IntentUtils
{
// MARK: - Methods

    fun getExtras(intent: Intent?, defaultValue: Bundle): Bundle {
        var result = defaultValue

        if (intent != null) {
            val extras = intent.extras

            if (extras != null) {
                result = extras
            }
        }

        return result
    }

    @JvmOverloads
    fun getExtra(intent: Intent?, key: String, defaultValue: Any? = null): Any? {
        var result = defaultValue

        if (intent != null && intent.hasExtra(key)) {
            val extras = intent.extras
            if (extras != null) {
                result = extras.get(key)
            }
        }

        return result
    }
}