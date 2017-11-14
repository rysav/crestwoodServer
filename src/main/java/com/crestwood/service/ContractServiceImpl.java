package com.crestwood.service;

import com.crestwood.exceptions.AlreadyExistsException;
import com.crestwood.exceptions.NotFoundException;
import com.crestwood.model.Contract;
import com.crestwood.persistance.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ryan on 11/10/17.
 */
@org.springframework.stereotype.Service("contractService")
public class ContractServiceImpl extends Service implements ContractService {

    private final ContractRepository contractRepository;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Override
    public List<Contract> getAll() {
        return (List<Contract>)contractRepository.findAll();
    }

    @Override
    public Contract getById(String id) throws NotFoundException {
        Contract temp = contractRepository.findOne(id);
        if (temp == null ) {
            throw new NotFoundException("Contract Not Found");
        }
        return temp;
    }

    @Override
    public void add(Contract contract) throws AlreadyExistsException {
        if(!contractRepository.exists(contract.getId())){
            //Contract nonNull = new Contract(contract);
            contractRepository.save(contract);
        } else {
            throw new AlreadyExistsException("Contract already exists");
        }

    }

    @Override
    public void update(String id, Contract contract) throws NotFoundException {

        Contract temp = contractRepository.findOne(id);
        if (temp == null) {
            throw new NotFoundException("Contract Not Found");
        }
        try {
            contractRepository.save((Contract) updateObject(contract, temp));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) throws NotFoundException {

        if(contractRepository.exists(id)) {
            contractRepository.delete(id);
        } else {
            throw new NotFoundException("Contract Not Found");
        }
    }
}
