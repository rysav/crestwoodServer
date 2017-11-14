package com.crestwood.service;

import com.crestwood.exceptions.AlreadyExistsException;
import com.crestwood.exceptions.NotFoundException;
import com.crestwood.model.PayDate;
import com.crestwood.model.PayDateKey;
import com.crestwood.model.PaymentPlan;
import com.crestwood.persistance.PayDateRepository;
import com.crestwood.persistance.PaymentPlanRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.List;

/**
 * Created by ryan on 10/14/17.
 */
@org.springframework.stereotype.Service("paymentPlanService")
public class PaymentPlanServiceImpl extends Service implements PaymentPlanService {


    private final PaymentPlanRepository paymentPlanRepository;
    private final PayDateRepository payDateRepository;

    @Autowired
    public PaymentPlanServiceImpl(PaymentPlanRepository paymentPlanRepository, PayDateRepository payDateRepository) {
        this.paymentPlanRepository = paymentPlanRepository;
        this.payDateRepository = payDateRepository;
    }

    @Override
    public List<PaymentPlan> getAll() {
        return (List<PaymentPlan>)paymentPlanRepository.findAll();
    }

    @Override
    public PaymentPlan getById(String id) throws NotFoundException {
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
    public void update(String id, PaymentPlan paymentPlan) throws NotFoundException {

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
    public void delete(String id) throws NotFoundException {

        PaymentPlan temp = paymentPlanRepository.findOne(id);
        if (temp == null) {
            throw new NotFoundException("Payment Plan Not Found");
        }
        paymentPlanRepository.delete(id);
    }

    @Override
    public void addPayDate(PayDate payDate) throws AlreadyExistsException {
        if (payDateRepository.exists(new PayDateKey(payDate.getPayDateKey().getPaymentPlan(), payDate.getPayDateKey().getDueDate()))){
            throw new AlreadyExistsException("Pay date already exists for that pay plan");
        }
        payDateRepository.save(payDate);
    }

    @Override
    public void deletePayDate(PayDate payDate) throws NotFoundException {

        if (!payDateRepository.exists(new PayDateKey(payDate.getPayDateKey().getPaymentPlan(), payDate.getPayDateKey().getDueDate()))){
            throw new NotFoundException("Pay date not found for that pay plan");
        }
        payDateRepository.delete(payDate);
    }

    @Override
    public PaymentPlan getPayPlanByUserId(String userId) {


        return null;
    }




}
