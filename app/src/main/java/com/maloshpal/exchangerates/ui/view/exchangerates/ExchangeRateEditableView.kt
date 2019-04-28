package com.maloshpal.exchangerates.ui.view.exchangerates

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

import com.maloshpal.exchangerates.R
import org.androidannotations.annotations.AfterViews

import org.androidannotations.annotations.EViewGroup
import org.androidannotations.annotations.ViewById
import java.lang.NumberFormatException

@EViewGroup(R.layout.layout_exchange_rate_editable)
open class ExchangeRateEditableView : LinearLayout {

// MARK: - Properties

    var amountChangeListener: IMoneyAmountEditListener? = null

    @ViewById(R.id.label_exchange_rate_name)
    internal lateinit var nameText: TextView

    @ViewById(R.id.label_exchange_rate_amount)
    internal lateinit var amountText: EditText

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
    }

// MARK: - Methods

    @AfterViews
    fun onInit() {
        this.amountText.addTextChangedListener(object : TextWatcher{

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                this@ExchangeRateEditableView.amountChangeListener?.onStartChangingAmount()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try {
                    val amount: Long = requireNotNull(p0?.toString()?.toLong()) { "Unexpected null in string to long conversion" }
                    this@ExchangeRateEditableView.amountChangeListener?.onAmountChanged(amount)
                }
                catch (ex: NumberFormatException) {
                    Log.w(TAG, "Cannot parse amount from user's input")
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // do nothing
            }
        })
    }

    fun setName(name: String) {
        this.nameText.text = name
    }

    fun setMoneyAmount(moneyAmount: Long) {
        this.amountText.setText(moneyAmount.toString())
    }

// MARK: - Inner types

    interface IMoneyAmountEditListener {
        fun onStartChangingAmount()
        fun onAmountChanged(amount: Long)
    }

// MARK: - Companion

    companion object {

        private val TAG = ExchangeRateEditableView::class.java.simpleName

        fun newView(context: Context): ExchangeRateEditableView {
            return ExchangeRateEditableView_.build(context)
        }
    }
}