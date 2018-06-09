package com.crestwood.service;

import com.crestwood.exceptions.NotFoundException;
import com.crestwood.model.Transaction;
import com.crestwood.model.User;
import com.crestwood.persistance.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ryan on 10/14/17.
 */
@org.springframework.stereotype.Service("transactionService")
public class TransactionServiceImpl extends Service implements TransactionService {


    private final TransactionRepository transactionRepository;
    private final UserService userService;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }


    @Override
    public List<Transaction> getAll() {
        return (List<Transaction>) transactionRepository.findAll();
    }

    @Override
    public Transaction getById(int id) throws NotFoundException {
        Transaction temp = transactionRepository.findOne(id);
        if (temp == null ) {
            throw new NotFoundException("Transaction does not exist");
        }
        return temp;
    }

    @Override
    public void add(Transaction transaction) {

        //TO DO check for existing transaction
        if (transaction.getTime() == null) {
            transaction.setTime(new Date(Calendar.getInstance().getTime().getTime()));
        }
        transactionRepository.save(transaction);

    }

    @Override
    public void update(int id, Transaction transaction) throws NotFoundException {

        Transaction temp = transactionRepository.findOne(id);
        if (temp == null) {
            throw new NotFoundException("Transaction Not Found");
        }
        try {
            transactionRepository.save((Transaction) updateObject(transaction, temp));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) throws NotFoundException {

        if (!transactionRepository.exists(id)) {
            throw new NotFoundException("Transaction does not exist");
        }
        Transaction transaction = transactionRepository.findOne(id);
        if (transaction.getTransactionNum() != null && !transaction.getTransactionNum().equals("Auto")) {
            throw new NotFoundException("Cannot delete paid transactions");
        }
        User user = userService.getUser(transaction.getUserId());
        user.setAmountDue(user.getAmountDue() + (transaction.getAmount() * -1));
        if (!transaction.getDescription().equals("Rent Charge")) {
            if (transaction.getAmount() < 0) {
                user.getContract().setAmountPaid(user.getContract().getAmountPaid() + (transaction.getAmount()));
            } else {
                user.getContract().setExtraCharges(user.getContract().getExtraCharges() + (transaction.getAmount() * -1));
            }
        }
        transactionRepository.delete(id);

    }

    @Override
    public List<Transaction> getByUserId(String userId)  {

        return transactionRepository.findByUserIdLike(userId);
    }


}
