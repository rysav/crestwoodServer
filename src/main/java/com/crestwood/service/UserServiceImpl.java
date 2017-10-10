package com.crestwood.service;

import com.crestwood.exceptions.AlreadyExistsException;
import com.crestwood.exceptions.NotFoundException;
import com.crestwood.model.User;
import com.crestwood.persistance.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ryan on 10/9/17.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User getUser(int userId) throws NotFoundException {

        User temp = userRepository.findOne(userId);
        if (temp == null) {
            throw new NotFoundException("User does not exist");
        }
        return temp;
    }

    @Override
    public void addUser(User user) throws AlreadyExistsException {
        User temp = userRepository.findOne(user.getUserId());
        if (temp != null) {
            throw new AlreadyExistsException("User does not exist");
        }
        userRepository.save(user);

    }

    @Override
    public void updateUser(User user) throws NotFoundException {
        User temp = userRepository.findOne(user.getUserId());
        if (temp == null) {
            throw new NotFoundException("User does not exist");
        }
        userRepository.save(user);
    }

    @Override
    public void deleteUser(int userId) throws NotFoundException {
        User temp = userRepository.findOne(userId);
        if (temp == null) {
            throw new NotFoundException("User does not exist");
        }
        userRepository.delete(userId);
    }
}
