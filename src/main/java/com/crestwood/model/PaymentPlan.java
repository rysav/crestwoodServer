package com.crestwood.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by ryan on 10/14/17.
 */
@Entity
@Table(name = "paymentPlan")
public class PaymentPlan implements Serializable{
    @Id
    private String id;


    @OneToMany(mappedBy = "paymentPlan", fetch=FetchType.EAGER)
    private Collection<Contract> contracts;

    @OneToMany(mappedBy = "payDateKey.paymentPlan", fetch=FetchType.EAGER)
    private List<PayDate> payDates;

    public PaymentPlan(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PayDate> getPayDates() {
        return payDates;
    }

    /*public void setPayDates(List<PayDate> payDates) {
        this.payDates = payDates;
    }*/
}
