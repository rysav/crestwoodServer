package com.crestwood.rest;

import com.crestwood.exceptions.NotFoundException;
import com.crestwood.model.PaymentDetails;
import com.crestwood.service.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by ryan on 10/18/17.
 */
@RestController
@Api(value="payment", description="Operations pertaining to payments")
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @ApiOperation(value = "Makes payment for specific user", response = Iterable.class)
    @RequestMapping(value="/makePayment", method = RequestMethod.POST)
    void makePayment(String userId, double amountPaid, int transactionNum, Date timeStamp, String notes) throws NotFoundException {
         paymentService.makePayment(userId, amountPaid, transactionNum, timeStamp, notes);
    }

    @ApiOperation(value = "adds charge to specific user", response = Iterable.class)
    @RequestMapping(value="/addCharge", method = RequestMethod.POST)
    void addCharge(String userId, double amountCharge, String notes) throws NotFoundException  {
        paymentService.addCharge(userId, amountCharge, notes);
    }

    @ApiOperation(value = "gets how much is owed and transaction history for specific user", response = Iterable.class)
    @RequestMapping(method = RequestMethod.GET)
    PaymentDetails getPaymentDetails(String userId) throws NotFoundException {
        return paymentService.getPaymentDetails(userId);
    }
}
