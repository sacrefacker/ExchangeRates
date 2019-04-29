package com.maloshpal.exchangerates.ui.activity.base

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.maloshpal.exchangerates.ui.util.IntentUtils
import com.maloshpal.exchangerates.R
import com.maloshpal.exchangerates.app.InjectionHolder
import com.maloshpal.exchangerates.ui.util.DialogFragmentManager
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.OptionsItem
import org.androidannotations.annotations.ViewById

@EActivity
abstract class BaseAppActivity : AppCompatActivity() {

// MARK: - Properties

    protected val intentExtras: Bundle get() = IntentUtils.getExtras(this.intent, Bundle())

    lateinit var dialogFragmentManager: DialogFragmentManager

    @ViewById(R.id.label_custom_activity_title)
    internal lateinit var titleText: TextView

// MARK: - Methods

    @AfterViews
    protected open fun onInitInterface() {

        // Lock orientation of the current activity
        onLockOrientation()

        InjectionHolder.instance().bootstrapComponent.inject(this)

        this.dialogFragmentManager = DialogFragmentManager(this)
    }

    override fun setTitle(title: CharSequence) {
        this.titleText.text = title
    }

    @OptionsItem(android.R.id.home)
    fun onClickHome(): Boolean {
        val fragment = supportFragmentManager.findFragmentById(R.id.content_fragment)
        var result = false

        if (fragment is FragmentHomePressed) {
            result = fragment.onHomePressed()
        }

        if (!result) {
            result = onHomePressed()
        }

        // Done
        return result
    }

    open fun onHomePressed(): Boolean {
        return false
    }

    override fun onBackPressed() {
        val fragment = this.supportFragmentManager.findFragmentById(R.id.content_fragment)
        var result = false

        if (fragment is FragmentBackPressed) {
            result = fragment.onBackPressed()
        }

        if (!result) {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val fragment = this.supportFragmentManager.findFragmentById(R.id.content_fragment)
        var result = false

        if (fragment is ActivityResultReceiver) {
            result = (fragment as ActivityResultReceiver).onResultReceived(requestCode, resultCode, data)
        }

        if (!result) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

// MARK: - Private methods

    private fun onLockOrientation() {
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

// MARK: - Inner types

    interface FragmentHomePressed {
        fun onHomePressed(): Boolean
    }

    interface FragmentBackPressed {
        fun onBackPressed(): Boolean
    }

    interface ActivityResultReceiver {
        fun onResultReceived(requestCode: Int, resultCode: Int, data: Intent?): Boolean
    }
}
