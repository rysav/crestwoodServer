package com.crestwood.rest;

/**
 * Created by ryan on 11/10/17.
 */

import com.crestwood.exceptions.AlreadyExistsException;
import com.crestwood.exceptions.NotFoundException;
import com.crestwood.model.Contract;
import com.crestwood.model.Transaction;
import com.crestwood.service.ContractService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Api(value="contract", description="Operations pertaining to contracts")
@RequestMapping("/contract")
public class ContractController {
    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @ApiOperation(value = "This gets a list of all contract", response = Iterable.class)
    @RequestMapping(method = RequestMethod.GET)
    List<Contract> getAll()  {
        return contractService.getAll();
    }

    @ApiOperation(value = "gets contract by Id", response = Iterable.class)
    @RequestMapping(value="{id}", method = RequestMethod.GET)
    Contract getContract(String id) throws NotFoundException {
        return contractService.getById(id);
    }

    @ApiOperation(value = "adds contract")
    @RequestMapping(method = RequestMethod.POST)
    void add( Contract contract) throws AlreadyExistsException {
        contractService.add(contract);
    }

    @ApiOperation(value = "update contract")
    @RequestMapping(method = RequestMethod.PUT)
    void update(String id, Contract contract) throws NotFoundException {
        contractService.update(id, contract);
    }

    @ApiOperation(value = "removes contract by Id", response = Iterable.class)
    @RequestMapping(method = RequestMethod.DELETE)
    void delete(String id) throws NotFoundException {
        contractService.delete(id);
    }
}
