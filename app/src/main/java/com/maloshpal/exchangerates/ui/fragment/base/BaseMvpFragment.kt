package com.maloshpal.exchangerates.ui.fragment.base

import android.os.Bundle
import android.support.v4.app.Fragment

import com.arellomobile.mvp.MvpDelegate

import org.androidannotations.annotations.EFragment

@EFragment
open class BaseMvpFragment : Fragment()
{
// MARK: - Properties

    val mvpDelegate: MvpDelegate<out BaseMvpFragment> by lazy { MvpDelegate(this) }

// MARK: - Functions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mvpDelegate.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        this.mvpDelegate.onAttach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        this.mvpDelegate.onSaveInstanceState(outState)
        this.mvpDelegate.onDetach()
    }

    override fun onStop() {
        super.onStop()
        this.mvpDelegate.onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.mvpDelegate.onDetach()
        this.mvpDelegate.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()

        // We leave the screen and respectively all fragments will be destroyed
        if (this.activity?.isFinishing == true) {
            this.mvpDelegate.onDestroy()
            return
        }

        // See https://github.com/Arello-Mobile/Moxy/issues/24
        var anyParentIsRemoving = false
        var parent : Fragment? = this.parentFragment
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }

        if (this.isRemoving || anyParentIsRemoving) {
            this.mvpDelegate.onDestroy()
        }
    }
}