package com.philconnal.shoezone.controller.helper;


import com.philconnal.shoezone.common.enums.AppStatus;
import com.philconnal.shoezone.common.enums.UserRole;
import com.philconnal.shoezone.common.exception.anotationvalidation.BadRequestException;
import com.philconnal.shoezone.common.exception.errors.MyBadRequestException;
import com.philconnal.shoezone.common.exception.errors.MyExistedException;
import com.philconnal.shoezone.common.exception.errors.MyParseDateException;
import com.philconnal.shoezone.common.validation.Validator;
import com.philconnal.shoezone.controller.request.NewerRequest;
import com.philconnal.shoezone.entity.NewUser;
import com.philconnal.shoezone.entity.User;
import com.philconnal.shoezone.service.NewerService;
import com.philconnal.shoezone.service.UserService;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class NewerHelper {
    private final UserService userService;
    private final NewerService newerService;

    public NewerHelper(UserService userService, NewerService newerService) {
        this.userService = userService;
        this.newerService = newerService;
    }

    public NewUser addNewUser(NewerRequest request) {
        try {
            Validator.validateEmail(request.getEmail().trim());

            if (!request.getPassword().trim().equals(request.getConfirmed().trim()))
                throw new MyBadRequestException("Invalid Confirmed password!");

            User userByUsername = userService.getUserByUsername(request.getUsername().trim());
            User userByEmail = userService.getUserByEmail(request.getEmail().trim());
            if (userByUsername != null) {
                System.out.println(userByUsername);
                throw new MyExistedException(
                        String.format("Username %s already exist!", request.getUsername()));

            }
            if (userByEmail != null) {
                System.out.println(userByEmail);
                throw new MyExistedException(
                        String.format("Email %s has been used!", request.getEmail()));

            }

            NewUser newUser = new NewUser();
            newUser.setFirst_name(request.getFirst_name().trim());
            newUser.setLast_name(request.getLast_name().trim());
            newUser.setAddress(request.getAddress());
            newUser.setEmail(request.getEmail());
            newUser.setAddress(request.getAddress());
            newUser.setPhone(request.getPhone());
            newUser.setUsername(request.getUsername());
            newUser.setPassword(request.getPassword());
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            formatter.setLenient(false);

            Date date = null;
            try {
                date = formatter.parse(request.getDob());
            } catch (ParseException e) {
                throw new MyParseDateException("Invalid date(dd-MM-yyyy): " + request.getDob());
            }
            System.out.println("Date: " + date);
            newUser.setDob(date);
            return newUser;

        } catch (
                BadRequestException | MyParseDateException exception) {
            throw new RuntimeException(exception);
        }
    }

    public User createNewAccount(NewUser newUser) {
        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        user.setRole(UserRole.USER);
        user.setStatus(AppStatus.ACTIVE);
        return user;
    }
}
