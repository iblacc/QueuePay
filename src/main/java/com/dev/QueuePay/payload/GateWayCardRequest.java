package com.dev.QueuePay.payload;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class GateWayCardRequest {
    private String pan;
    private String name;
    private enum CardType{ VISA, MASTERCARD, VERVE};
    private String cvv;
    private String pin;
    @DateTimeFormat
    private Date expiryDate;
    private Double amount;
    private String token;

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

