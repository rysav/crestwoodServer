package com.crestwood.service;

import com.crestwood.exceptions.NotFoundException;
import com.crestwood.mail.GoogleMail;
import com.crestwood.model.*;
import com.crestwood.persistance.PaymentPlanRepository;
import com.crestwood.persistance.TransactionRepository;
import com.crestwood.persistance.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.mail.MessagingException;
import java.util.*;
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
    private final String RENT_DUE = "Rent Charge";



    @Value("${email.username}")
    private String username;
    @Value("${email.password}")
    private String password;
    @Value("${email.admin}")
    private String adminEmail;

    @Autowired
    public PaymentServiceImpl(UserRepository userRepository, TransactionRepository transactionRepository, PaymentPlanRepository paymentPlanRepository,
                              UserService userService, TransactionService transactionService) {
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
        user.getContract().setAmountPaid(user.getContract().getAmountPaid() + amountPaid);
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
        String adminReceiptMessage = "Payment of " + amountPaid + " was made by user: " + userId;


        try {
            GoogleMail.Send(username, password, user.getEmail(), "Payment Confirmation", emailMessage);
            GoogleMail.Send(username, password, adminEmail, userId + " Payment Receipt", adminReceiptMessage);
        } catch (MessagingException e) {
            //invalid email
        }

    }

    @Override
    public PaymentDetails getPaymentDetails(String userId) throws NotFoundException {

        User u = userService.getUser(userId);
        return new PaymentDetails(u.getAmountDue(), u.getContract().getLeftToPay(),  transactionService.getByUserId(userId));
    }

    @Override
    public void addCharge(String userId, double amountCharge, String notes) throws NotFoundException {

        User user = userService.getUser(userId);
        user.setAmountDue(user.getAmountDue() + amountCharge);
        if (!notes.equals(RENT_DUE)) {
            user.getContract().setExtraCharges(user.getContract().getExtraCharges() + amountCharge);
        }
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
        double rentAmount = Math.round((contract.getFullAmountDue()/dates.size()) * 100.00) / 100.00;

        for (PayDate payDate: dates) {
            Date date =  payDate.getPayDateKey().getDueDate();
            int difference = calcDifference(new Date(), date);
            double fee = calcFee(difference, rentAmount, user.getAmountDue());
            String message = calcMessage(fee, rentAmount);

            if (fee != 0) {
                try {
                    addCharge(user.getUserId(), fee, message);
                } catch (NotFoundException e) {
                    //add log -- this shouldn't happen
                }
            }

        }
    }

    public String calcMessage(double fee, double rentAmount) {
        if (fee == rentAmount) {
            return RENT_DUE;
        } else if (fee == 0) {
            return "";
        } else {
            return "Late Rent Fee";
        }
    }

    public int calcDifference(Date thisDate, Date dueDate) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(thisDate);
        cal2.setTime(dueDate);
        int thisMonth = cal1.get(Calendar.MONTH);
        int thisDay = cal1.get(Calendar.DAY_OF_MONTH);
        int payMonth = cal2.get(Calendar.MONTH);
        int payDay = cal2.get(Calendar.DAY_OF_MONTH);
        int difference = -1;
        if (thisMonth == payMonth) {
            difference = thisDay - payDay;

        } else if ((((thisMonth - 1) % 12) + 12) % 12 == payMonth) {
            int daysInPayMonth = cal2.getActualMaximum(Calendar.DAY_OF_MONTH);
            int preflow = daysInPayMonth - payDay;
            difference = preflow + thisDay;
        }

        return difference;
    }

    public double calcFee(int difference, double rentAmount, double amountOwed) {
        double fee = 0;
        if (difference == 0) {
            fee = rentAmount;
        } else if (difference == 2 && amountOwed >= rentAmount) {
            fee = 10;
        } else if (difference > 2 && difference <= 8 && amountOwed >= rentAmount) {
            fee = 5;
        }
        return fee;
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
