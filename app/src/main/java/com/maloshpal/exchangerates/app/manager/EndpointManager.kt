package com.maloshpal.exchangerates.app.manager

import com.maloshpal.exchangerates.R
import com.maloshpal.exchangerates.app.ApplicationImpl

interface IEndpointManager {
    val endpoint: String
}

object EndpointManager : IEndpointManager {

    override val endpoint: String
        get() = getCurrentEndpoint()

    private fun getCurrentEndpoint(): String {
        val context = ApplicationImpl.context
        return context.getString(R.string.config_default_app_endpoint)
    }
}