package com.crestwood.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.crestwood.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by ryan on 10/20/17.
 */
@Component
public class PaymentUpdater {

    private static final Logger log = LoggerFactory.getLogger(PaymentUpdater.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    private PaymentService paymentService;

    @Autowired
    private PaymentUpdater(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Scheduled(cron = "${my.cron.paymentUpdater}")
    public void updatePayments() {
        paymentService.updatePayments();
    }

}
