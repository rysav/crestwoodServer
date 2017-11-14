package com.crestwood.rest;

import com.crestwood.exceptions.AlreadyExistsException;
import com.crestwood.exceptions.NotFoundException;
import com.crestwood.model.PayDate;
import com.crestwood.model.PaymentPlan;
import com.crestwood.service.PaymentPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ryan on 10/14/17.
 */
@RestController
@Api(value="paymentPlan", description="Operations pertaining to payment plans")
@RequestMapping("/payment/plan")
public class PaymentPlanController {


    private final PaymentPlanService paymentPlanService;

    @Autowired
    public PaymentPlanController(PaymentPlanService paymentPlanService) {
        this.paymentPlanService = paymentPlanService;
    }

    @ApiOperation(value = "This gets a list of all payment plans", response = Iterable.class)
    @RequestMapping(method = RequestMethod.GET)
    List<PaymentPlan> getAll()  {
        return paymentPlanService.getAll();
    }

    @ApiOperation(value = "gets pay plan by Id", response = Iterable.class)
    @RequestMapping(value="{planId}", method = RequestMethod.GET)
    PaymentPlan getUser(String id) throws NotFoundException {
        return paymentPlanService.getById(id);
    }



    @ApiOperation(value = "adds payment plan")
    @RequestMapping(method = RequestMethod.POST)
    void add(PaymentPlan paymentPlan) throws AlreadyExistsException {
        paymentPlanService.add(paymentPlan);
    }

    @ApiOperation(value = "update payment plan")
    @RequestMapping(method = RequestMethod.PUT)
    void update(String id, PaymentPlan paymentPlan) throws NotFoundException {
        paymentPlanService.update(id, paymentPlan);
    }

    @ApiOperation(value = "removes payment plan by Id", response = Iterable.class)
    @RequestMapping(method = RequestMethod.DELETE)
    void delete(String id) throws NotFoundException {
        paymentPlanService.delete(id);
    }


    @ApiOperation(value="add pay date for specified payment plan", response= Iterable.class)
    @RequestMapping(value="/date", method = RequestMethod.POST)
    void addPayDate(PayDate payDate) throws AlreadyExistsException {
        paymentPlanService.addPayDate(payDate.getPaymentPlanId(), payDate.getDueDate());
    }

    @ApiOperation(value="remove pay date for specified payment plan", response= Iterable.class)
    @RequestMapping(value="/date", method = RequestMethod.DELETE)
    void deletePayDate(PayDate payDate) throws AlreadyExistsException, NotFoundException {
        paymentPlanService.deletePayDate(payDate.getPaymentPlanId(), payDate.getDueDate());
    }
}
