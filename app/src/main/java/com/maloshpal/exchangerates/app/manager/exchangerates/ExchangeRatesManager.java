package com.maloshpal.exchangerates.app.manager.exchangerates;

import android.annotation.SuppressLint;

import com.maloshpal.exchangerates.app.manager.ApiService;
import com.maloshpal.exchangerates.mvp.model.exchangerates.ExchangeRateListModel;
import com.maloshpal.exchangerates.mvp.model.exchangerates.IExchangeRatesManager;
import com.maloshpal.exchangerates.mvp.model.base.IManagerCallback;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ExchangeRatesManager implements IExchangeRatesManager {

    private ApiService api;

    public ExchangeRatesManager(ApiService api) {
        this.api = api;
    }

    @SuppressLint("CheckResult")
    @Override
    public void requestExchangeRates(@Nullable String baseCurrency, @NotNull IManagerCallback<ExchangeRateListModel> callback) {
        this.api.getExchangeRates(baseCurrency)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> callback.handleResponse(response),
                        throwable -> callback.handleError());

    }
}

