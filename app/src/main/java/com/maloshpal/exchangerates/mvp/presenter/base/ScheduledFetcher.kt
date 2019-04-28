package com.maloshpal.exchangerates.mvp.presenter.base

import com.maloshpal.exchangerates.mvp.model.base.BaseModel
import com.maloshpal.exchangerates.mvp.model.base.IManagerCallback

import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class ScheduledFetcher<T : BaseModel>(
        private val scheduledExecutorService: ScheduledExecutorService,
        private val delaySeconds: Long,
        private val callback: IManagerCallback<T>
) {

// MARK: - Properties

    private val lock = Any()

    private var currentRunnable: Runnable? = null

    @Volatile
    private var isStopped: Boolean = false

    @Volatile
    private var scheduledFuture: ScheduledFuture<*>? = null

// MARK: - Public functions

    fun start(requestFunction: ((IManagerCallback<T>) -> Unit)) {
        stop()

        this.isStopped = false
        this.currentRunnable = Runnable {
            synchronized(lock) {
                requestFunction.invoke(CallbackWrapper())
            }
        }

        scheduleCurrentRunnable(FIRST_EXECUTE_DELAY_SECONDS)
    }

    fun stop() {
        synchronized(lock) {
            this.scheduledFuture?.cancel(true)
            this.scheduledFuture = null
            this.isStopped = true
        }
    }

// MARK: - Private functions

    private fun scheduleCurrentRunnable(delay: Long) {
        synchronized(lock) {
            if (!this.isStopped) {
                this.scheduledFuture = scheduledExecutorService.schedule(currentRunnable!!, delay, TimeUnit.SECONDS)
            }
        }
    }

// MARK: - Inner Types

    private inner class CallbackWrapper : IManagerCallback<T> {

        override fun handleResponse(response: T) {
            this@ScheduledFetcher.callback.handleResponse(response)
            scheduleCurrentRunnable(delaySeconds)
        }

        override fun handleError() {
            this@ScheduledFetcher.callback.handleError()
        }
    }

    companion object {
        private const val FIRST_EXECUTE_DELAY_SECONDS = 1L
    }
}
