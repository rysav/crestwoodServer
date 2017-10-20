package com.crestwood.service;

import com.crestwood.exceptions.NotFoundException;
import com.crestwood.mail.GoogleMail;
import com.crestwood.model.PaymentDetails;
import com.crestwood.model.Transaction;
import com.crestwood.model.User;
import com.crestwood.persistance.PaymentPlanRepository;
import com.crestwood.persistance.TransactionRepository;
import com.crestwood.persistance.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import java.sql.Date;
import java.util.Calendar;

/**
 * Created by ryan on 10/18/17.
 */
@org.springframework.stereotype.Service("paymentService")
public class PaymentServiceImpl extends Service implements PaymentService {


    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final PaymentPlanRepository paymentPlanRepository;
    private final UserService userService;
    private final TransactionService transactionService;

    @Autowired
    public PaymentServiceImpl(UserRepository userRepository, TransactionRepository transactionRepository, PaymentPlanRepository paymentPlanRepository, UserService userService, TransactionService transactionService) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.paymentPlanRepository = paymentPlanRepository;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @Override
    public void makePayment(String userId, double amountPaid, int transactionNum, java.util.Date timeStamp, String notes) throws NotFoundException {
        User user = userService.getUser(userId);
        user.setAmountDue(user.getAmountDue() - amountPaid);
        //check for if they paid too much?
        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setAmount(0 - amountPaid);
        transaction.setMethod("Online");
        transaction.setDescription(notes);
        transaction.setTransactionNum(transactionNum);
        transaction.setTime(timeStamp);

        userService.updateUser(user);
        transactionService.add(transaction);
    }

    @Override
    public PaymentDetails getPaymentDetails(String userId) throws NotFoundException {
        try {
            GoogleMail.Send("ryssavage", "Whale24135", "ryssavage@gmail.com", "test auto email", "hey you are awesome");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return new PaymentDetails(userService.getUser(userId).getAmountDue(), transactionService.getByUserId(userId));
    }

    @Override
    public void addCharge(String userId, double amountCharge, String notes) throws NotFoundException {

        User user = userService.getUser(userId);
        user.setAmountDue(user.getAmountDue() + amountCharge);
        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setAmount(amountCharge);
        transaction.setMethod("Online");
        transaction.setDescription(notes);
        transaction.setTransactionNum(-1);
        transaction.setTime(new Date(Calendar.getInstance().getTime().getTime()));

        userService.updateUser(user);
        transactionService.add(transaction);
    }

    @Override
    public void updatePayments() {





    }
}
