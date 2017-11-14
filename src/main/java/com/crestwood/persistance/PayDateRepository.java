package com.crestwood.persistance;

import com.crestwood.model.PayDate;
import com.crestwood.model.PayDateKey;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by ryan on 11/14/17.
 */
@Transactional
public interface PayDateRepository extends CrudRepository<PayDate, PayDateKey>{
}
