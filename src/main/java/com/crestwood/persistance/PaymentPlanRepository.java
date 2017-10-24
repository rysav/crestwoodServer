package com.crestwood.persistance;

import com.crestwood.model.PaymentPlan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * Created by ryan on 10/14/17.
 */
@Transactional
public interface PaymentPlanRepository extends CrudRepository<PaymentPlan, Integer> {

    @Query(value = "SELECT p.id, p.payment, p.dueDate, p.gracePeriod FROM PaymentPlan p Join user u WHERE userId=:userId", nativeQuery = true)
    //@Query("SELECT p.id, p.payment, p.dueDate, p.gracePeriod FROM PaymentPlan p Join user u ON p.userId = p.userId WHERE userId=:userId")

        //@Query("Select PaymentPlan from PaymentPlan where id = 1")
    PaymentPlan findPaymentPlanByUserId(@Param("userId")String userId);
}
