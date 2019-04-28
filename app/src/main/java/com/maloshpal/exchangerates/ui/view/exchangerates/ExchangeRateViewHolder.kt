package com.maloshpal.exchangerates.ui.view.exchangerates

import com.maloshpal.exchangerates.mvp.view.exchangerates.ExchangeRateViewModel
import com.maloshpal.exchangerates.ui.view.ClickableViewHolder
import com.maloshpal.exchangerates.ui.view.IOnItemViewClickListener

class ExchangeRateViewHolder(view: ExchangeRateListItemView)
    : ClickableViewHolder<ExchangeRateListItemView, ExchangeRateViewModel>(view) {

    override fun bind(dataModel: ExchangeRateViewModel, position: Int, listener: IOnItemViewClickListener) {
        super.bind(dataModel, position, listener)

        this.typedView.setName(dataModel.id)
        this.typedView.setMoneyAmount(dataModel.amount)
    }
}