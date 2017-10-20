package com.crestwood.service;

import com.crestwood.exceptions.NotFoundException;
import com.crestwood.model.PaymentPlan;

import java.util.List;

/**
 * Created by ryan on 10/14/17.
 */
public interface PaymentPlanService {
    List<PaymentPlan> getAll();
    PaymentPlan getById(int id) throws NotFoundException;
    void add(PaymentPlan paymentPlan);
    void update(int id, PaymentPlan paymentPlan) throws NotFoundException;
    void delete(int id) throws NotFoundException;

    PaymentPlan getPayPlanByUserId(String userId);
}
