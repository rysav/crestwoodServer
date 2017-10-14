package com.crestwood.service;

import com.crestwood.model.Transaction;

import java.util.List;

/**
 * Created by ryan on 10/14/17.
 */
public interface TransactionService {
    List<Transaction> getAll();
    Transaction getById(int id);
    void add(Transaction transaction);
    void update(int id, Transaction transaction);
    void delete(int id);
}
