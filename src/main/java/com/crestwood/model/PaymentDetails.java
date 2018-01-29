package com.crestwood.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by ryan on 10/18/17.
 */
public class PaymentDetails {


    private double amountDue;
    private double amountLeftOnContract;
    private List<Transaction> transactionHistory;

    public PaymentDetails(double amountDue, double totalDue, List<Transaction> transactionHistory) {
        this.amountDue = amountDue;
        this.amountLeftOnContract = totalDue;
        this.transactionHistory = transactionHistory;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(List<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public double getAmountLeftOnContract() {
        return amountLeftOnContract;
    }

    public void setAmountLeftOnContract(double amountLeftOnContract) {
        this.amountLeftOnContract = amountLeftOnContract;
    }
}
