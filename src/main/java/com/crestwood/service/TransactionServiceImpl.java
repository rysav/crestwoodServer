package com.crestwood.service;

import com.crestwood.exceptions.NotFoundException;
import com.crestwood.model.Transaction;
import com.crestwood.persistance.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ryan on 10/14/17.
 */
@org.springframework.stereotype.Service("transactionService")
public class TransactionServiceImpl extends Service implements TransactionService {


    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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
        transactionRepository.delete(id);
    }

    @Override
    public List<Transaction> getByUserId(String userId)  {

        return transactionRepository.findByUserIdLike(userId);
    }
}
