package com.dev.QueuePay.payload;


public class Performance {
    private int totalVolume = 0 ;
    private Double totalValue = 0.00;
    private Integer totalSuccessfulTransaction = 0;
    private Integer totalFailedTransaction = 0;
    private Double balance = 0.0;

    public int getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(int totalVolume) {
        this.totalVolume = totalVolume;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Integer getTotalSuccessfulTransaction() {
        return totalSuccessfulTransaction;
    }

    public void setTotalSuccessfulTransaction(Integer totalSuccessfulTransaction) {
        this.totalSuccessfulTransaction = totalSuccessfulTransaction;
    }

    public Integer getTotalFailedTransaction() {
        return totalFailedTransaction;
    }

    public void setTotalFailedTransaction(Integer totalFailedTransaction) {
        this.totalFailedTransaction = totalFailedTransaction;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}