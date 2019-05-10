package com.maloshpal.exchangerates.manager

import com.maloshpal.exchangerates.mvp.model.base.IBaseModel

@Deprecated("Not used")
class Response<T : IBaseModel>(val responseModel: T?, val error: String?)