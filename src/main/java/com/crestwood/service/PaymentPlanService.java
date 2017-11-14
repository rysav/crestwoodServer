package com.crestwood.service;

import com.crestwood.exceptions.AlreadyExistsException;
import com.crestwood.exceptions.NotFoundException;
import com.crestwood.model.PaymentPlan;

import java.sql.Date;
import java.util.List;

/**
 * Created by ryan on 10/14/17.
 */
public interface PaymentPlanService {
    List<PaymentPlan> getAll();
    PaymentPlan getById(String id) throws NotFoundException;
    void add(PaymentPlan paymentPlan);
    void update(String id, PaymentPlan paymentPlan) throws NotFoundException;
    void delete(String id) throws NotFoundException;

    void addPayDate(String id, Date date) throws AlreadyExistsException;
    void deletePayDate(String id, Date date) throws NotFoundException;

    PaymentPlan getPayPlanByUserId(String userId);
}
