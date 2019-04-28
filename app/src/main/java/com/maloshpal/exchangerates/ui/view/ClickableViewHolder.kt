package com.maloshpal.exchangerates.ui.view

import android.view.View
import com.maloshpal.exchangerates.ui.util.DebounceClickListener

open class ClickableViewHolder<V : View, T>(itemView: V) : TypedViewHolder<V>(itemView) {

// MARK: - Variables

    private var listener: IOnItemViewClickListener? = null

    var explicitPosition: Int = 0
        private set

    private val viewClickListener = View.OnClickListener { view ->
        this.listener?.onItemViewClick(view, this.explicitPosition)
    }

// MARK: - Construction

    init {
        this.typedView.setOnClickListener(DebounceClickListener(
                onClick = { view ->
                    view?.let { this.listener?.onItemViewClick(view, this.adapterPosition) }
                } ))
    }

// MARK: - Methods

    fun setCanClick(canClick: Boolean) {
        if (canClick) {
            this.typedView.setOnClickListener(this.viewClickListener)
        }
        else {
            this.typedView.isClickable = false
        }
    }

    open fun bind(dataModel: T, position: Int, listener: IOnItemViewClickListener) {
        setPosition(position)
        this.listener = listener
    }

// MARK: - Private methods

    private fun setPosition(position: Int) {
        this.explicitPosition = position
    }
}
