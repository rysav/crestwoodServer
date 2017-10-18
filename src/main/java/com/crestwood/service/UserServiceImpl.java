package com.crestwood.service;

import com.crestwood.exceptions.AlreadyExistsException;
import com.crestwood.exceptions.NotFoundException;
import com.crestwood.model.User;
import com.crestwood.persistance.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 10/9/17.
 */
@Service("userService")
public class UserServiceImpl extends com.crestwood.service.Service implements UserService {

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
    public User getUser(String userId) throws NotFoundException {

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
        try {
            userRepository.save((User)updateObject(user, temp));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(String userId) throws NotFoundException {
        User temp = userRepository.findOne(userId);
        if (temp == null) {
            throw new NotFoundException("User does not exist");
        }
        userRepository.delete(userId);
    }

    @Override
    public List<User> getPossibleUsers(String subString) {
        List<User> users = (List<User>) userRepository.findAll();
        List<User> toReturn = new ArrayList<User>();
        for (User u : users) {
            String fullName = u.getFirstName().toLowerCase() + " " + u.getLastName().toLowerCase();
            if (fullName.startsWith(subString.toLowerCase())) {
                toReturn.add(u);
            }
        }

        for (User u : users) {
            String fullName = u.getFirstName().toLowerCase() + " " + u.getLastName().toLowerCase();
            if (!fullName.startsWith(subString.toLowerCase()) && fullName.contains(subString.toLowerCase())) {
                toReturn.add(u);
            }
        }
        return toReturn;
    }
}
