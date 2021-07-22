package com.philconnal.shoezone.controller;

import com.philconnal.shoezone.common.exception.anotationvalidation.BadRequestException;
import com.philconnal.shoezone.controller.api.APIName;
import com.philconnal.shoezone.controller.helper.NewerHelper;
import com.philconnal.shoezone.controller.request.NewerRequest;
import com.philconnal.shoezone.controller.response.RestApiResponse;
import com.philconnal.shoezone.entity.NewUser;
import com.philconnal.shoezone.entity.User;
import com.philconnal.shoezone.service.NewerService;
import com.philconnal.shoezone.service.UserService;
import com.philconnal.shoezone.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(APIName.SIGN_UP)
public class RegistrationController {
    private final NewerHelper newerHelper;
    private final ResponseUtil responseUtil;
    private final UserService userService;
    private final NewerService newerService;

    public RegistrationController(NewerHelper newerHelper, ResponseUtil responseUtil, UserService userService, NewerService newerService) {
        this.newerHelper = newerHelper;
        this.responseUtil = responseUtil;
        this.userService = userService;
        this.newerService = newerService;
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RestApiResponse> addUser(@Valid @RequestBody NewerRequest request) {
        try {

            final NewUser newUser = newerHelper.addNewUser(request);

            User newAccount = newerHelper.createNewAccount(newUser);

            userService.saveUser(newAccount);

            newerService.saveUser(newUser);

            return responseUtil.successResponse(newUser);

        } catch (BadRequestException exception) {
            throw new RuntimeException(exception);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<RestApiResponse> getAllNewer() {

        List<NewUser> allUsers = newerService.getAllUsers();

        return responseUtil.successResponse(allUsers);
    }
}
