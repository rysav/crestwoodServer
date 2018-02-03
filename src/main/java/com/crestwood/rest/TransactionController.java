package com.crestwood.rest;

import com.crestwood.exceptions.AlreadyExistsException;
import com.crestwood.exceptions.NotFoundException;
import com.crestwood.model.Transaction;
import com.crestwood.service.TransactionService;
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
@Api(value="transaction", description="Operations pertaining to transactions")
@RequestMapping("/transaction")
public class TransactionController {


    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @ApiOperation(value = "This gets a list of all transactions", response = Iterable.class)
    @RequestMapping(method = RequestMethod.GET)
    List<Transaction> getAll()  {
        return transactionService.getAll();
    }

    @ApiOperation(value = "gets transaction by Id", response = Iterable.class)
    @RequestMapping(value="{id}", method = RequestMethod.GET)
    Transaction getTransaction(int id) throws NotFoundException {
        return transactionService.getById(id);
    }

    @ApiOperation(value = "adds transaction")
    @RequestMapping(method = RequestMethod.POST)
    void add( Transaction transaction) throws AlreadyExistsException {
        transactionService.add(transaction);
    }

    @ApiOperation(value = "update transaction")
    @RequestMapping(method = RequestMethod.PUT)
    void update(int id, Transaction transaction) throws NotFoundException {
        transactionService.update(id, transaction);
    }

    @ApiOperation(value = "removes transaction by Id", response = Iterable.class)
    @RequestMapping(method = RequestMethod.DELETE)
    void delete(int id) throws NotFoundException {
        transactionService.delete(id);
    }


    @ApiOperation(value = "gets all transactions for a given user", response = Iterable.class)
    @RequestMapping(value="/{userId}", method = RequestMethod.GET)
    List<Transaction> getTransactionsByUserId(String userId) {
        return transactionService.getByUserId(userId);
    }
}
