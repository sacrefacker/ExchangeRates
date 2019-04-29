package com.maloshpal.exchangerates.ui.view.exchangerates

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.maloshpal.exchangerates.R
import com.maloshpal.exchangerates.ui.util.MoneyUtils
import com.maloshpal.exchangerates.ui.util.ViewUtils

import org.androidannotations.annotations.EViewGroup
import org.androidannotations.annotations.ViewById

@EViewGroup(R.layout.layout_exchange_rate_list_item)
open class ExchangeRateListItemView : LinearLayout {

// MARK: - Properties

    @ViewById(R.id.label_exchange_rate_name)
    internal lateinit var nameText: TextView

    @ViewById(R.id.label_exchange_rate_amount)
    internal lateinit var amountText: TextView

// MARK: - Construction

    constructor(context: Context) : super(context) {
        configureView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        configureView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        configureView(context)
    }

    private fun configureView(context: Context) {
        this.orientation = HORIZONTAL
        val viewHeight = context.resources.getDimensionPixelSize(R.dimen.exchange_rate_view_height)
        this.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, viewHeight)
        val paddingSize = context.resources.getDimensionPixelOffset(R.dimen.exchange_rate_view_padding)
        setPadding(paddingSize, 0, paddingSize, 0)
        this.gravity = Gravity.CENTER_VERTICAL
        ViewUtils.setRippleBackground(this)
    }

// MARK: - Methods

    fun setName(name: String) {
        this.nameText.text = name
    }

    fun setMoneyAmount(moneyAmount: Long) {
        this.amountText.text = MoneyUtils.convertToString(this.context, moneyAmount)
    }

// MARK: - Companion

    companion object {

        fun newView(context: Context): ExchangeRateListItemView {
            return ExchangeRateListItemView_.build(context)
        }
    }
}