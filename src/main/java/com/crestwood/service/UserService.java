package com.crestwood.service;

import com.crestwood.exceptions.AlreadyExistsException;
import com.crestwood.exceptions.NotFoundException;
import com.crestwood.model.User;

import java.util.List;

/**
 * Created by ryan on 10/9/17.
 */
public interface UserService {

    List<User> getAllUsers();
    User getUser(String userId) throws NotFoundException;
    void addUser(User user) throws AlreadyExistsException;
    void updateUser(User user) throws NotFoundException;
    void deleteUser(String userId) throws NotFoundException;
    List<User> getPossibleUsers(String subString);

    List<User> sendMassEmail(String message, String title) throws NotFoundException;
}
