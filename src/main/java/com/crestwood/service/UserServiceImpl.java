package com.crestwood.service;

import com.crestwood.exceptions.AlreadyExistsException;
import com.crestwood.exceptions.NotFoundException;
import com.crestwood.mail.GoogleMail;
import com.crestwood.model.User;
import com.crestwood.persistance.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 10/9/17.
 */
@Service("userService")
public class UserServiceImpl extends com.crestwood.service.Service implements UserService {

    private final UserRepository userRepository;

    @Value("${email.username}")
    private String username;
    @Value("${email.password}")
    private String password;

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
        User nonNull = new User(user);
        userRepository.save(nonNull);

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

    @Override
    public List<User> sendMassEmail(String message, String title) throws NotFoundException {
        List<User> users = (List<User>) userRepository.findAll();
        List<User> failedUsers = new ArrayList<User>();
        for (User u : users) {
            try {
                GoogleMail.Send(username, password, u.getEmail(), title, message);
            } catch (MessagingException e) {
                //invalid email
                failedUsers.add(u);
            }
        }
        return failedUsers;
    }
}
