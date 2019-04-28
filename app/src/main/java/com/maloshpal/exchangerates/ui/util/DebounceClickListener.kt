package com.maloshpal.exchangerates.ui.util

import android.view.View

class DebounceClickListener(
        private val debounceIntervalInMs: Long = DEFAULT_DEBOUNCE_INTERVAL,
        private val onClick: (view: View?) -> Unit
) : View.OnClickListener {

// MARK: - Properties

    private var lastClickTimeStamp: Long = 0

// MARK: - Methods

    override fun onClick(v: View?) {
        val currentClickTimestamp = System.currentTimeMillis()
        if (currentClickTimestamp - lastClickTimeStamp < debounceIntervalInMs) {
            return
        }
        this.lastClickTimeStamp = currentClickTimestamp
        this.onClick.invoke(v)
    }

// MARK: - Companion

    companion object {

        private const val DEFAULT_DEBOUNCE_INTERVAL = 1000L
    }
}