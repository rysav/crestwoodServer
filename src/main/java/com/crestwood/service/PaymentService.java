package com.crestwood.service;

import com.crestwood.exceptions.NotFoundException;
import com.crestwood.model.PaymentDetails;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by ryan on 10/18/17.
 */
public interface PaymentService {
    void makePayment(String userId, double amountPaid, int transactionNum, Date timeStamp, String notes) throws NotFoundException;
    PaymentDetails getPaymentDetails(String userId) throws NotFoundException;
    void addCharge(String userId, double amountCharge, String notes) throws NotFoundException;
    void updatePayments();
}
