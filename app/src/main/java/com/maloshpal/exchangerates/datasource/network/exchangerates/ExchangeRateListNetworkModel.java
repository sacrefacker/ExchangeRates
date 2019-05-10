package com.maloshpal.exchangerates.datasource.network.exchangerates;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.maloshpal.exchangerates.mvp.model.exchangerates.IExchangeRateListModel;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

// FIXME clean implementation
public class ExchangeRateListNetworkModel implements IExchangeRateListModel {

// MARK: - Fields

    @Expose
    @SerializedName("base")
    private String base;

    @Expose
    @SerializedName("date")
    private String date;

    @Expose
    @SerializedName("rates")
    private Map<String, Double> rates;

// MARK: - Construction

    public ExchangeRateListNetworkModel(String base, String date, Map<String, Double> rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

// MARK: - Getters and setters

    @Override
    public @NotNull String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    @Override
    public @NotNull String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public @NotNull Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
