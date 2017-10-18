package com.crestwood.persistance;

import com.crestwood.model.Transaction;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by ryan on 10/14/17.
 */
@Transactional
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    List<Transaction> findByUserIdLike(String userId);
}
