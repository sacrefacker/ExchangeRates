package com.maloshpal.exchangerates.mvp.model.base

interface IManagerCallback<T : BaseModel> {

    fun handleResponse(response: T)
    fun handleError()
}