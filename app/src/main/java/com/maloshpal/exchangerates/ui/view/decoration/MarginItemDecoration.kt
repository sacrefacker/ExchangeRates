package com.maloshpal.exchangerates.ui.view.decoration

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class MarginItemDecoration(
        private val horizontalMargin: Int,
        private val verticalMargin: Int
): RecyclerView.ItemDecoration() {

// MARK: - Methods

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = this.horizontalMargin
        outRect.right = this.horizontalMargin
        outRect.top = this.verticalMargin
        outRect.bottom = this.verticalMargin
    }
}