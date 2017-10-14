package com.crestwood.persistance;

import com.crestwood.model.PaymentPlan;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by ryan on 10/14/17.
 */
@Transactional
public interface PaymentPlanRepository extends CrudRepository<PaymentPlan, Integer> {

}
