package com.crestwood.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by ryan on 11/14/17.
 */
@Entity
@Table(name = "payDate")
public class PayDate implements Serializable{


    @EmbeddedId
    private PayDateKey payDateKey;


    private String paymentPlanId;


    private Date dueDate;

    @MapsId("paymentPlanId")
    @ManyToOne
    private PaymentPlan paymentPlan;



    public PayDate(){}
    public PayDate(String id, Date date) {
        payDateKey = new PayDateKey(id, date);
        paymentPlanId = id;
        dueDate = date;
    }

    /*public PaymentPlan getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(PaymentPlan paymentPlan) {
        this.paymentPlan = paymentPlan;
    }*/

    /*public PayDateKey getPayDateKey() {
        return payDateKey;
    }

    public void setPayDateKey(PayDateKey payDateKey) {
        this.payDateKey = payDateKey;
    }*/

    public String getPaymentPlanId() {
        return paymentPlanId;
    }

    public void setPaymentPlanId(String paymentPlanId) {
        this.paymentPlanId = paymentPlanId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /*public PaymentPlan getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(PaymentPlan paymentPlan) {
        this.paymentPlan = paymentPlan;
    }*/
}
