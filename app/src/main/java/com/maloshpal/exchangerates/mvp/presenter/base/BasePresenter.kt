package com.maloshpal.exchangerates.mvp.presenter.base

import android.util.Log
import com.arellomobile.mvp.MvpPresenter
import com.maloshpal.exchangerates.mvp.view.base.IBaseView

abstract class BasePresenter<T : IBaseView> : MvpPresenter<T>()
{
    protected val isViewAttached: Boolean get() = attachedViews.size > 0

    private val tag = javaClass.simpleName

    override fun attachView(view: T) {
        Log.i(this.tag, "attachView")
        super.attachView(view)
    }

    override fun detachView(view: T) {
        Log.i(this.tag, "detachView")
        super.detachView(view)
    }
}
