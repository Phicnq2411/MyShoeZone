package com.philconnal.shoezone.controller.user;


import com.philconnal.shoezone.common.enums.AppStatus;
import com.philconnal.shoezone.common.exception.anotationvalidation.BadRequestException;
import com.philconnal.shoezone.common.exception.errors.MyExistedException;
import com.philconnal.shoezone.common.exception.errors.MyNotFoundException;
import com.philconnal.shoezone.common.validation.Validator;
import com.philconnal.shoezone.controller.api.APIName;
import com.philconnal.shoezone.controller.helper.UserHelper;
import com.philconnal.shoezone.entity.User;
import com.philconnal.shoezone.service.UserService;
import com.philconnal.shoezone.util.ResponseUtil;
import com.philconnal.shoezone.util.RestApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(APIName.USER_API)
public class UserController {

    private final UserService userService;
    private final UserHelper userHelper;
    private final ResponseUtil responseUtil;

    public UserController(UserService userService, UserHelper userHelper, ResponseUtil responseUtil) {
        this.userService = userService;
        this.userHelper = userHelper;
        this.responseUtil = responseUtil;
    }

    // Get all users
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<RestApiResponse> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return responseUtil.successResponse(allUsers);
    }

    // Add user
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RestApiResponse> addUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        try {
            Validator.validateEmail(createUserRequest.getEmail());

            User oneByUsername = userService.getUserByUsername(createUserRequest.getUsername().trim());
            if (oneByUsername != null)
                throw new MyExistedException(String.format("User %s already exist", createUserRequest.getUsername()));

            User oneByEmail = userService.getUserByEmail(createUserRequest.getEmail());
            if (oneByEmail != null)
                throw new MyExistedException(String.format("User %s already exist", createUserRequest.getEmail()));

            User user = userHelper.createUser(createUserRequest);
            userService.saveUser(user);

            return responseUtil.successResponse(user);

        } catch (BadRequestException exception) {
            throw new RuntimeException(exception);
        }
    }

    // Update user
    @RequestMapping(method = RequestMethod.PUT, path = "{username}")
    public ResponseEntity<RestApiResponse> updateUser(@Valid @PathVariable String username, @RequestBody UpdateUserRequest userRequest) {
        User user;

        if (Validator.isEmailFormat(username)) {
            user = userService.getUserByEmail(username);
        } else {
            user = userService.getUserByUsername(username);
        }
        userHelper.notFoundUser(user, username);

        userHelper.updateUser(user, userRequest);

        userService.saveUser(user);

        return responseUtil.successResponse(user);
    }

    //Delete user
    @RequestMapping(method = RequestMethod.DELETE, path = "{username}")
    public ResponseEntity<RestApiResponse> deleteUser(@PathVariable String username) {
        User user;
        if (Validator.isEmailFormat(username))
            user = userService.getUserByEmail(username);
        else {
            user = userService.getUserByUsername(username);
        }
        if (user == null)
            throw new MyNotFoundException(String.format("User %s not found", username));
        user.setStatus(AppStatus.INACTIVE);
        userService.saveUser(user);
        return responseUtil.successResponse("Ok");
    }
}
