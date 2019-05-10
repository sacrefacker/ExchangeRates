package com.maloshpal.exchangerates.manager

import com.maloshpal.exchangerates.mvp.model.base.IBaseModel
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

data class CallResult<T: IBaseModel> internal constructor(
        val data: T? = null,
        val error: String? = null
) {
    companion object {
        fun <T: IBaseModel> result(data: T) = CallResult(data = data)
        fun <T: IBaseModel> error(error: String) = CallResult<T>(error = error)
    }
}

class AsyncCallback<T : IBaseModel>(
        private val continuation: Continuation<CallResult<T>>
) : IDataSourceCallback<T> {

    override fun handleResponse(response: T) {
        this.continuation.resume(CallResult.result(response))
    }

    override fun handleError(error: String) {
        this.continuation.resume(CallResult.error(error))
    }
}