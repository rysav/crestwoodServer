package com.crestwood.rest;

import com.crestwood.exceptions.AlreadyExistsException;
import com.crestwood.exceptions.NotFoundException;
import com.crestwood.model.User;
import com.crestwood.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ryan on 10/7/17.
 */
@RestController
@Api(value="onlinestore", description="Operations pertaining to products in Online Store")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "This gets a list of all users", response = Iterable.class)
    @RequestMapping(method = RequestMethod.GET)
    List<User> getAllUsers()  {
        return userService.getAllUsers();
    }

    @ApiOperation(value = "gets user by Id", response = Iterable.class)
    @RequestMapping(value="{userId}", method = RequestMethod.GET)
    User getUser(int userId) throws NotFoundException {
        return userService.getUser(userId);
    }

    @ApiOperation(value = "gets all users whos name starts with the input or contains the input", response = Iterable.class)
    @RequestMapping(value="/search", method = RequestMethod.GET)
    List<User> getPossibleUsers(String subString) {
        return userService.getPossibleUsers(subString);
    }

    @ApiOperation(value = "adds user")
    @RequestMapping(method = RequestMethod.POST)
    void addUser(User user) throws AlreadyExistsException {
        userService.addUser(user);
    }

    @ApiOperation(value = "update user")
    @RequestMapping(method = RequestMethod.PUT)
    void updateUser(int userId, User user) throws NotFoundException {
        userService.updateUser(userId, user);
    }

    @ApiOperation(value = "removes user by Id", response = Iterable.class)
    @RequestMapping(method = RequestMethod.DELETE)
    void deleteUser(int userId) throws NotFoundException {
        userService.deleteUser(userId);
    }
}
