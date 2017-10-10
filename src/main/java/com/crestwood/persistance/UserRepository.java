package com.crestwood.persistance;

import com.crestwood.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by ryan on 10/9/17.
 */
@Transactional
public interface UserRepository extends CrudRepository<User, Integer> {
}
