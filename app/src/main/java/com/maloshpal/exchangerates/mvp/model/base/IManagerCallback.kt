package com.maloshpal.exchangerates.mvp.model.base

interface IManagerCallback<T : IBaseModel> {

    fun handleResponse(response: T)
    fun handleError()
}