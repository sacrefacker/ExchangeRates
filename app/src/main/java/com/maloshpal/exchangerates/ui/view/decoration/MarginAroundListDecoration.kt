package com.maloshpal.exchangerates.ui.view.decoration

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class MarginAroundListDecoration private constructor(
        private val marginStart: Int,
        private val marginEnd: Int,
        private val horizontal: Boolean
) : RecyclerView.ItemDecoration() {

// MARK: - Methods

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val itemPosition = parent.getChildAdapterPosition(view)

        if (itemPosition == 0) {
            if (this.horizontal) {
                outRect.left = this.marginStart
            }
            else {
                outRect.top = this.marginStart
            }
        }
        else {
            val itemCount = state.itemCount
            if (itemCount > 0 && itemPosition == state.itemCount - 1) {
                if (this.horizontal) {
                    outRect.right = this.marginEnd
                }
                else {
                    outRect.bottom = this.marginEnd
                }
            }
        }
    }

// MARK: - Companion

    companion object {

        fun vertical(marginSize: Int): MarginAroundListDecoration {
            return MarginAroundListDecoration(marginSize, marginSize, false)
        }

        fun vertical(marginTop: Int, marginBottom: Int): MarginAroundListDecoration {
            return MarginAroundListDecoration(marginTop, marginBottom, false)
        }

        fun horizontal(marginSize: Int): MarginAroundListDecoration {
            return MarginAroundListDecoration(marginSize, marginSize, true)
        }

        fun horizontal(marginLeft: Int, marginRight: Int): MarginAroundListDecoration {
            return MarginAroundListDecoration(marginLeft, marginRight, true)
        }
    }
}