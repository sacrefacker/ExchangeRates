package com.maloshpal.exchangerates.app

import com.maloshpal.exchangerates.app.dagger.components.BootstrapComponent
import kotlin.properties.Delegates.notNull

class InjectionHolder private constructor() {

// MARK: - Properties

    var bootstrapComponent: BootstrapComponent by notNull()

// MARK: - Companion

    private object SingletonHolder {
        val SHARED_INSTANCE = InjectionHolder()
    }

    companion object {
        fun instance(): InjectionHolder = SingletonHolder.SHARED_INSTANCE
    }
}
