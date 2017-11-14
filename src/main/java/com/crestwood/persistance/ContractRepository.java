package com.crestwood.persistance;

import com.crestwood.model.Contract;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by ryan on 11/10/17.
 */
@Transactional
public interface ContractRepository extends CrudRepository<Contract, String>{
}
