package com.crestwood.model;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by ryan on 11/14/17.
 */
@Embeddable
public class PayDateKey implements Serializable {

    private String paymentPlan;


    private Date date;

    public PayDateKey(){}
    public PayDateKey(String id, Date date) {
        paymentPlan = id;
        this.date = date;
    }

    public String getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(String paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
