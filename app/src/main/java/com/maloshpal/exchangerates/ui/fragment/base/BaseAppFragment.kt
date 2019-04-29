package com.maloshpal.exchangerates.ui.fragment.base

import android.content.Context
import com.maloshpal.exchangerates.ui.util.ClassUtils
import com.maloshpal.exchangerates.ui.activity.base.BaseAppActivity
import com.maloshpal.exchangerates.ui.util.DialogFragmentManager
import com.maloshpal.exchangerates.ui.util.SimpleDialogListener
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment

@EFragment
open class BaseAppFragment : BaseMvpFragment() {

// MARK: - Methods

    @AfterViews
    protected open fun onInitInterface() {
        // Do not remove - needed for correct generation of Fragment_ with OnViewChangedListener interface
    }

    override fun onStart() {
        super.onStart()

        // Handling ActionBar title with the fragment back stack?
        // @link http://stackoverflow.com/a/24674527
        if (this is ActivityTitleProvider) {
            getBaseActivity()?.title = this.activityTitle
        }
    }

    fun showFinishActivityDialog(onCancel: (() -> Unit)? = null) {
        getDialogManager().showExitAppDialog(
                listener = object : SimpleDialogListener() {

                    override fun onPositiveButtonClicked(requestCode: Int) {
                        super.onPositiveButtonClicked(requestCode)
                        closeApp()
                    }

                    override fun onNegativeButtonClicked(requestCode: Int) {
                        super.onNegativeButtonClicked(requestCode)
                        onCancel?.invoke()
                    }

                    override fun onCancel(requestCode: Int) {
                        super.onCancel(requestCode)
                        onCancel?.invoke()
                    }
                }
        )
    }

    fun closeApp() {
        this.activity?.finish()
    }

    protected fun getDialogManager(): DialogFragmentManager {
        val appActivity = this.activity as BaseAppActivity
        return appActivity.dialogFragmentManager
    }

// MARK: - Private methods

    private fun getBaseActivity(): BaseAppActivity? {
        val activity = this.activity
        return if (activity is BaseAppActivity) activity else null
    }

// MARK: - Inner types

    interface ActivityTitleProvider {
        val activityTitle: String
    }

// MARK: - Companion

    companion object {
        internal inline fun <reified T: BaseAppFragment> newInstance(context: Context): T {
            val fragmentClass = ClassUtils.getGeneratedClass(T::class.java)
            return instantiate(context, fragmentClass.canonicalName) as T
        }
    }
}