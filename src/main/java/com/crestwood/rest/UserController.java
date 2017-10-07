package com.crestwood.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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

    @ApiOperation(value = "This does nothing yet", response = Iterable.class)
    @RequestMapping(method = RequestMethod.GET)
    List<String> getAllUsers()  {
        System.out.print("getAllUsers()");
        return null;
    }
}
