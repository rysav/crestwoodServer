package com.crestwood.rest;

import com.crestwood.exceptions.AlreadyExistsException;
import com.crestwood.exceptions.NotFoundException;
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
    PaymentPlan getUser(int id) throws NotFoundException {
        return paymentPlanService.getById(id);
    }



    @ApiOperation(value = "adds payment plan")
    @RequestMapping(method = RequestMethod.POST)
    void add(PaymentPlan paymentPlan) throws AlreadyExistsException {
        paymentPlanService.add(paymentPlan);
    }

    @ApiOperation(value = "update payment plan")
    @RequestMapping(method = RequestMethod.PUT)
    void update(int id, PaymentPlan paymentPlan) throws NotFoundException {
        paymentPlanService.update(id, paymentPlan);
    }

    @ApiOperation(value = "removes payment plan by Id", response = Iterable.class)
    @RequestMapping(method = RequestMethod.DELETE)
    void delete(int id) throws NotFoundException {
        paymentPlanService.delete(id);
    }
}
