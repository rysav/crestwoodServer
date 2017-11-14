package com.crestwood.model;



import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Date;

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
    private String apartmentComplex;

    private Boolean parkingPass;
    private Integer unit;

    @Column(name="contract")
    private String contractId;
    @ManyToOne
    @MapsId("contractId")
    private Contract contract;

    private String gender;
    @ApiModelProperty(dataType = "org.joda.time.LocalDate")
    private Date birthday;
    private Double amountDue;

    private String customerStripeHash;
    private String bankAccountHash;
    private Boolean verifiedBankAccount;


    public User(){}

    public User(User u) {
        userId = u.getUserId();
        firstName = u.getFirstName();
        lastName = u.getLastName();
        email = u.getEmail();
        phone = u.getPhone();
        parkingPass = (u.getParkingPass() == null) ? false:u.getParkingPass();
        unit = (u.getUnit() ==null) ? 1:u.getUnit();
        contractId = (u.getContractId() == null) ? "none":u.getContractId();
        contract = u.getContract();
        gender = u.getGender();
        birthday = u.getBirthday();
        amountDue = (u.getAmountDue() == null) ? 0:u.getAmountDue();
        customerStripeHash = u.getCustomerStripeHash();
        bankAccountHash = u.getBankAccountHash();
        verifiedBankAccount = (u.getVerifiedBankAccount() == null) ? false:u.getVerifiedBankAccount();
        apartmentComplex = u.getApartmentComplex();

    }


    public String getApartmentComplex() {
        return apartmentComplex;
    }

    public void setApartmentComplex(String apartmentComplex) {
        this.apartmentComplex = apartmentComplex;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Contract getContract() {
        return contract;
    }

    /*public void setContract(Contract contract) {
        this.contract = contract;
    }*/

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

    public Boolean getParkingPass() {
        return parkingPass;
    }

    public void setParkingPass(Boolean parkingPass) {
        this.parkingPass = parkingPass;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
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

    public Double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(Double amountDue) {
        this.amountDue = amountDue;
    }



    public String getCustomerStripeHash() {
        return customerStripeHash;
    }

    public void setCustomerStripeHash(String customerStripeHash) {
        this.customerStripeHash = customerStripeHash;
    }

    public String getBankAccountHash() {
        return bankAccountHash;
    }

    public void setBankAccountHash(String bankAccountHash) {
        this.bankAccountHash = bankAccountHash;
    }

    public Boolean getVerifiedBankAccount() {
        return verifiedBankAccount;
    }

    public void setVerifiedBankAccount(Boolean verifiedBankAccount) {
        this.verifiedBankAccount = verifiedBankAccount;
    }
}
