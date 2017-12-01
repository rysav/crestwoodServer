package com.crestwood.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by ryan on 11/14/17.
 */
@Embeddable
public class PayDateKey implements Serializable {

    @Column(name="paymentPlanId")
    private String paymentPlan;


    private Date dueDate;


    public PayDateKey(){}
    public PayDateKey(String id, Date date) {
        paymentPlan = id;
        dueDate = date;
    }

    public String getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(String paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
