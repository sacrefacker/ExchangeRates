package com.maloshpal.exchangerates.mvp.model.exchangerates;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.maloshpal.exchangerates.mvp.model.base.BaseModel;

import java.util.Map;

public class ExchangeRateListModel extends BaseModel {

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

    public ExchangeRateListModel(String base, String date, Map<String, Double> rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

// MARK: - Getters and setters

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
