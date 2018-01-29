package com.crestwood.service;

import com.crestwood.exceptions.AlreadyExistsException;
import com.crestwood.exceptions.NotFoundException;
import com.crestwood.model.Contract;
import com.crestwood.model.PaymentPlan;

import java.util.List;

/**
 * Created by ryan on 11/10/17.
 */
public interface ContractService {

    List<Contract> getAll();
    Contract getById(String id) throws NotFoundException;
    void add(Contract contract) throws AlreadyExistsException;
    void update(Contract contract) throws NotFoundException;
    void delete(String id) throws NotFoundException;
}
