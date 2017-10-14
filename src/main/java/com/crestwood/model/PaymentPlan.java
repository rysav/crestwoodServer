package com.crestwood.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * Created by ryan on 10/14/17.
 */
@Entity
@Table(name = "paymentPlan")
public class PaymentPlan {
    @Id
    @ApiModelProperty(hidden = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private double payment;
    private int dueDate;
    private int gracePeriod;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public int getDueDate() {
        return dueDate;
    }

    public void setDueDate(int dueDate) {
        this.dueDate = dueDate;
    }

    public int getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(int gracePeriod) {
        this.gracePeriod = gracePeriod;
    }
}
