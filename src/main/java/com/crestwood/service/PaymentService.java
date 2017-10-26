package com.crestwood.service;

import com.crestwood.exceptions.NotFoundException;
import com.crestwood.model.PaymentDetails;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by ryan on 10/18/17.
 */
public interface PaymentService {
    void makePayment(String userId, double amountPaid, String transactionNum, Date timeStamp, String description) throws NotFoundException;
    PaymentDetails getPaymentDetails(String userId) throws NotFoundException;
    void addCharge(String userId, double amountCharge, String description) throws NotFoundException;
    void updatePayments();
}
