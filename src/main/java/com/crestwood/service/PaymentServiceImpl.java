package com.crestwood.service;

import com.crestwood.exceptions.NotFoundException;
import com.crestwood.mail.GoogleMail;
import com.crestwood.model.*;
import com.crestwood.persistance.PaymentPlanRepository;
import com.crestwood.persistance.TransactionRepository;
import com.crestwood.persistance.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Value("${email.username}")
    private String username;
    @Value("${email.password}")
    private String password;

    @Autowired
    public PaymentServiceImpl(UserRepository userRepository, TransactionRepository transactionRepository, PaymentPlanRepository paymentPlanRepository, UserService userService, TransactionService transactionService) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.paymentPlanRepository = paymentPlanRepository;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @Override
    public void makePayment(String userId, double amountPaid, String transactionNum, java.util.Date timeStamp, String notes, String method) throws NotFoundException {
        User user = userService.getUser(userId);
        user.setAmountDue(user.getAmountDue() - amountPaid);
        //check for if they paid too much?
        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setAmount(0 - amountPaid);
        transaction.setMethod(method);
        transaction.setDescription(notes);
        transaction.setTransactionNum(transactionNum);
        transaction.setTime(timeStamp);

        userService.updateUser(user);
        transactionService.add(transaction);
        String emailMessage = "Your payment of " + amountPaid + " has been received and recorded.";

        try {
            GoogleMail.Send(username, password, user.getEmail(), "Payment Confirmation", emailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PaymentDetails getPaymentDetails(String userId) throws NotFoundException {

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
        transaction.setTransactionNum("Auto");
        transaction.setTime(new Date(Calendar.getInstance().getTime().getTime()));

        userService.updateUser(user);
        transactionService.add(transaction);
    }

    @Override
    public void updatePayments() {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int numDaysThisMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<User> users = userService.getAllUsers();
        for (User u: users) {
            if (handleContract(u)) {
                handlePayment(u);
            }

        }
    }

    public void handlePayment(User user) {
        Contract contract = user.getContract();
        if(contract.getPaymentPlan() == null || contract.getPaymentPlanId() == "none") {
            return;
        }
        PaymentPlan paymentPlan = contract.getPaymentPlan();
        List<PayDate> dates = (List<PayDate>) paymentPlan.getPayDates();
        double amountDue = Math.round((contract.getFullAmountDue()/dates.size()) * 100.00) / 100.00;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Date current = new Date();
        cal1.setTime(current);

        for (PayDate payDate: dates) {
            Date date =  payDate.getPayDateKey().getDueDate();
            cal2.setTime(date);
            int thisMonth = cal1.get(Calendar.MONTH);
            int thisDay = cal1.get(Calendar.DAY_OF_MONTH);
            int payMonth = cal2.get(Calendar.MONTH);
            int payDay = cal2.get(Calendar.DAY_OF_MONTH);
            double fee = 0;
            int difference = 0;
            if (thisMonth == payMonth) {
                difference = payDay - thisDay;

            } else if ((thisMonth - 1) % 12 == payMonth) {
                int daysInPayMonth = cal1.getActualMaximum(payMonth);
                int preflow = daysInPayMonth - payDay;
                difference = preflow + thisDay;
            }

            if (difference == 0) {
                try {
                    addCharge(user.getUserId(), amountDue, "Rent Charge");
                } catch (NotFoundException e) {
                    //add log -- this shouldn't happen
                }
                fee = 0;
            } else if (difference == 2 && user.getAmountDue() >= amountDue) {
                fee = 10;
            } else if (difference > 2 && difference <= 8 && user.getAmountDue() >= amountDue) {
                fee = 5;
            }

            if (fee != 0) {
                try {
                    addCharge(user.getUserId(), fee, "Late Rent Fee");
                } catch (NotFoundException e) {
                    //add log -- this shouldn't happen
                }
            }

        }
    }

    public boolean handleContract(User user) {
        if (user.getContractId().equals("none") || user.getContract() == null) {
            return false;
        }
        Date currentDate = new Date();
        Date startDate = user.getContract().getStartDate();
        Date endDate = user.getContract().getEndDate();
        if (currentDate.before(startDate) || currentDate.after(endDate)) {
            long diff = currentDate.getTime() - endDate.getTime();
            int days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            if (days > 60) {
                try {
                    userService.deleteUser(user.getUserId());
                } catch (NotFoundException e) {
                    //log this -- shouldn't come here
                }
                return false;
            }
        }
        return true;
    }
}
