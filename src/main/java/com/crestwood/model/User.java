package com.crestwood.model;



import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ryan on 10/9/17.
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @ApiModelProperty(required = true)
    private String userId;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    @ApiModelProperty(required = true)
    private boolean parkingPass;
    private int unit;
    private int contractId;
    private String gender;
    private Date birthday;
    private double amountDue;
    private int paymentPlan;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isParkingPass() {
        return parkingPass;
    }

    public void setParkingPass(boolean parkingPass) {
        this.parkingPass = parkingPass;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public int getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(int paymentPlan) {
        this.paymentPlan = paymentPlan;
    }
}
