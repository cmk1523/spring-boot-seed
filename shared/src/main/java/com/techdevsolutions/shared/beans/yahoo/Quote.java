package com.techdevsolutions.shared.beans.yahoo;

import com.techdevsolutions.shared.beans.auditable.Auditable;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Quote extends Auditable implements Serializable {
    protected String symbol = "";
    protected Double high = null;
    protected Double low = null;
    protected Double open = null;
    protected Double close = null;
    protected Integer volume = null;
    protected Date date = null;

    public Quote() {
    }

    @Override
    public String toString() {
        return "Quote{" +
                "symbol='" + symbol + '\'' +
                ", high=" + high +
                ", low=" + low +
                ", open=" + open +
                ", close=" + close +
                ", volume=" + volume +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote = (Quote) o;
        return Objects.equals(symbol, quote.symbol) &&
                Objects.equals(high, quote.high) &&
                Objects.equals(low, quote.low) &&
                Objects.equals(open, quote.open) &&
                Objects.equals(close, quote.close) &&
                Objects.equals(volume, quote.volume) &&
                Objects.equals(date, quote.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(symbol, high, low, open, close, volume, date);
    }

    public Date getDate() {
        return date;
    }

    public Quote setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public Quote setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public Double getHigh() {
        return high;
    }

    public Quote setHigh(Double high) {
        this.high = high;
        return this;
    }

    public Double getLow() {
        return low;
    }

    public Quote setLow(Double low) {
        this.low = low;
        return this;
    }

    public Double getOpen() {
        return open;
    }

    public Quote setOpen(Double open) {
        this.open = open;
        return this;
    }

    public Double getClose() {
        return close;
    }

    public Quote setClose(Double close) {
        this.close = close;
        return this;
    }

    public Integer getVolume() {
        return volume;
    }

    public Quote setVolume(Integer volume) {
        this.volume = volume;
        return this;
    }
}
