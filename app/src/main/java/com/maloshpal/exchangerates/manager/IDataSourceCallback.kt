package com.maloshpal.exchangerates.manager

import com.maloshpal.exchangerates.mvp.model.base.IBaseModel

interface IDataSourceCallback<T : IBaseModel> {

    fun handleResponse(response: T)
    fun handleError(error: String)
}
