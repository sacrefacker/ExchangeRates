package com.maloshpal.exchangerates.ui.util

import android.app.Dialog
import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import com.maloshpal.exchangerates.R

class DialogFragmentManager(
        private val context: Context
) {

// MARK: - Properties: ErrorAlertDialog

    private var activeDialog: Dialog? = null
    private var previousActiveDialog: Dialog? = null

// MARK: - Methods: Dismiss active dialog

    fun dismissActiveDialog() {
        activeDialog?.dismiss()
        activeDialog = null
    }

// MARK: - Methods: Show previous dialog

    fun showPreviousDialog() {
        activeDialog = previousActiveDialog
        previousActiveDialog = null
        activeDialog?.show()
    }

// MARK: - Methods: Exit dialog

    fun showExitAppDialog(
            @StringRes messageId: Int = R.string.message_exit,
            @StringRes positiveButtonTextId: Int = R.string.button_exit,
            @StringRes negativeButtonTextId: Int = R.string.button_stay,
            listener: SimpleDialogListener
    ) {
        previousActiveDialog = activeDialog

        showTwoButtonDialog(
                messageId = messageId,
                positiveButtonTextId = positiveButtonTextId,
                negativeButtonTextId = negativeButtonTextId,
                listener = listener)
    }

// MARK: - Methods: Alert dialog

    fun showAlertDialog(@StringRes titleId: Int,
                        @StringRes messageId: Int,
                        @StringRes buttonTextId: Int = R.string.dialog_button_ok,
                        listener: SimpleDialogListener? = null) {

        showAlertDialog(
                this.context.getString(titleId),
                this.context.getString(messageId),
                this.context.getString(buttonTextId),
                listener)
    }

    fun showAlertDialog(title: CharSequence? = null,
                        message: CharSequence,
                        buttonText: CharSequence = this.context.getString(R.string.dialog_button_ok),
                        listener: SimpleDialogListener? = null) {

        activeDialog?.dismiss()

        activeDialog = AlertDialog.Builder(this.context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(buttonText) { _, _ -> listener?.onPositiveButtonClicked(0) }
                .setOnDismissListener { listener?.onDismiss(0) }
                .setOnCancelListener { listener?.onCancel(0) }
                .show()
    }

// MARK: - Methods: TwoButtonDialog

    fun showTwoButtonDialog(@StringRes titleId: Int? = null,
                            @StringRes messageId: Int,
                            @StringRes positiveButtonTextId: Int,
                            @StringRes negativeButtonTextId: Int,
                            listener: SimpleDialogListener? = null,
                            cancelable: Boolean = true) {

        showTwoButtonDialog(
                titleId?.let { this.context.getString(titleId) },
                this.context.getString(messageId),
                this.context.getString(positiveButtonTextId),
                this.context.getString(negativeButtonTextId),
                listener,
                cancelable)
    }

    fun showTwoButtonDialog(title: CharSequence? = null,
                            message: CharSequence,
                            positiveButtonText: CharSequence,
                            negativeButtonText: CharSequence,
                            listener: SimpleDialogListener? = null,
                            cancelable: Boolean = true) {

        activeDialog?.dismiss()

        activeDialog = AlertDialog.Builder(this.context)
                .setCancelable(cancelable)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText) { _, _ -> listener?.onPositiveButtonClicked(0) }
                .setNegativeButton(negativeButtonText) { _, _ -> listener?.onNegativeButtonClicked(0) }
                .setOnDismissListener { listener?.onDismiss(0) }
                .setOnCancelListener { listener?.onCancel(0) }
                .show()
    }
}
