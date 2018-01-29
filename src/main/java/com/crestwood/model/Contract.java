package com.crestwood.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by ryan on 11/10/17.
 */
@Entity
@Table(name = "contract")
public class Contract {

    @Id
    @ApiModelProperty(required = true)
    private String id;
    private Date startDate;
    private Date endDate;
    private Double deposit;
    private Double refundable;
    private Double fullAmountDue;
    private Double extraCharges = Double.valueOf(0);
    private Double amountPaid = Double.valueOf(0);
    @Transient
    private double leftToPay;

    @Column(name = "paymentPlan")
    private String paymentPlanId;
    @ManyToOne
    @MapsId("paymentPlanId")
    private PaymentPlan paymentPlan;


    public Double getFullAmountDue() {
        return fullAmountDue;
    }

    public void setFullAmountDue(Double fullAmountDue) {
        this.fullAmountDue = fullAmountDue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getRefundable() {
        return refundable;
    }

    public void setRefundable(Double refundable) {
        this.refundable = refundable;
    }

    public String getPaymentPlanId() {
        return paymentPlanId;
    }

    public void setPaymentPlanId(String paymentPlanId) {
        this.paymentPlanId = paymentPlanId;
    }

    public PaymentPlan getPaymentPlan() {
        return paymentPlan;
    }

    /*public void setPaymentPlan(PaymentPlan paymentPlan) {
        this.paymentPlan = paymentPlan;
    }*/

    public Double getExtraCharges() {
        return extraCharges;
    }

    public void setExtraCharges(Double extraCharges) {
        this.extraCharges = extraCharges;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getLeftToPay() {
        if (extraCharges == null ) {extraCharges = Double.valueOf(0);}
        if (amountPaid == null) {amountPaid = Double.valueOf(0);}
        return (fullAmountDue + extraCharges - amountPaid);
    }
}
