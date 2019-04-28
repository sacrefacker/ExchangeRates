package com.maloshpal.exchangerates.ui.util

abstract class SimpleDialogListener(
        private val chainedListener: SimpleDialogListener? = null
) {

// MARK: - Methods

    open fun onPositiveButtonClicked(requestCode: Int) {
        chainedListener?.onPositiveButtonClicked(requestCode)
    }

    open fun onNegativeButtonClicked(requestCode: Int) {
        chainedListener?.onNegativeButtonClicked(requestCode)
    }

    open fun onNeutralButtonClicked(requestCode: Int) {
        chainedListener?.onNeutralButtonClicked(requestCode)
    }

    open fun onDismiss(requestCode: Int) {
        chainedListener?.onDismiss(requestCode)
    }

    open fun onCancel(requestCode: Int) {
        chainedListener?.onCancel(requestCode)
    }
}
