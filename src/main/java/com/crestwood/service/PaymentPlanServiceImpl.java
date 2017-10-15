package com.crestwood.service;

import com.crestwood.exceptions.NotFoundException;
import com.crestwood.model.PaymentPlan;
import com.crestwood.persistance.PaymentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ryan on 10/14/17.
 */
@org.springframework.stereotype.Service("paymentPlanService")
public class PaymentPlanServiceImpl extends Service implements PaymentPlanService {


    private final PaymentPlanRepository paymentPlanRepository;

    @Autowired
    public PaymentPlanServiceImpl(PaymentPlanRepository paymentPlanRepository) {
        this.paymentPlanRepository = paymentPlanRepository;
    }

    @Override
    public List<PaymentPlan> getAll() {
        return (List<PaymentPlan>)paymentPlanRepository.findAll();
    }

    @Override
    public PaymentPlan getById(int id) throws NotFoundException {
        PaymentPlan temp = paymentPlanRepository.findOne(id);
        if (temp == null ) {
            throw new NotFoundException("Payment Plan Not Found");
        }
        return temp;
    }

    @Override
    public void add(PaymentPlan paymentPlan) {

        //TO DO: add check for existing payment plan
        paymentPlanRepository.save(paymentPlan);

    }

    @Override
    public void update(int id, PaymentPlan paymentPlan) throws NotFoundException {

        PaymentPlan temp = paymentPlanRepository.findOne(id);
        if (temp == null) {
            throw new NotFoundException("Payment Plan Not Found");
        }
        try {
            paymentPlanRepository.save((PaymentPlan) updateObject(paymentPlan, temp));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) throws NotFoundException {

        PaymentPlan temp = paymentPlanRepository.findOne(id);
        if (temp == null) {
            throw new NotFoundException("Payment Plan Not Found");
        }
        paymentPlanRepository.delete(id);
    }
}
