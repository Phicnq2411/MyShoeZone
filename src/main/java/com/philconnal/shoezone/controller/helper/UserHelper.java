package com.philconnal.shoezone.controller.helper;

import com.philconnal.shoezone.common.enums.AppStatus;
import com.philconnal.shoezone.common.enums.UserRole;
import com.philconnal.shoezone.common.exception.errors.MyBadRequestException;
import com.philconnal.shoezone.common.exception.errors.MyExistedException;
import com.philconnal.shoezone.common.exception.errors.MyNotFoundException;
import com.philconnal.shoezone.common.validation.Validator;
import com.philconnal.shoezone.controller.request.CreateUserRequest;
import com.philconnal.shoezone.controller.request.UpdateUserRequest;
import com.philconnal.shoezone.entity.User;
import com.philconnal.shoezone.service.UserService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class UserHelper {
    @Autowired
    private UserService userService;

    public User createUser(CreateUserRequest createUserRequest) {
        Validator.validateEmail(createUserRequest.getEmail());
        User user = new User();
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(createUserRequest.getPassword());
        user.setUsername(createUserRequest.getUsername());
        user.setStatus(AppStatus.ACTIVE);
        user.setRole(UserRole.USER);
        return user;
    }


    public void notFoundUser(User user, String message) {
        if (user == null)
            throw new MyNotFoundException(String.format("User %s not found", message));
    }

    public void updateUser(User user, UpdateUserRequest userRequest) {
        if (userRequest.getUsername() != null) {

            if (userRequest.getUsername().trim().isEmpty())
                throw new MyBadRequestException("Username is empty");
            else {
                if (!user.getUsername().equals(userRequest.getUsername().trim())) {
                    User userByUsername = userService.getUserByUsername(userRequest.getUsername().trim());
                    if (userByUsername != null)
                        throw new MyExistedException(
                                String.format("User %s already exist", userRequest.getUsername()));
                    user.setUsername(userRequest.getUsername());
                }

            }

        }
        if (userRequest.getPassword() != null) {

            if (userRequest.getPassword().isEmpty())
                throw new MyBadRequestException("Password is empty");

            user.setPassword(userRequest.getPassword());
        }
        if (userRequest.getEmail() != null) {

            if (userRequest.getEmail().isEmpty())
                throw new MyBadRequestException("Email is empty");
            else {
                if (!user.getEmail().equals(userRequest.getEmail().trim())) {

                    Validator.validateEmail(userRequest.getEmail());

                    User userByEmail = userService.getUserByEmail(userRequest.getEmail().trim());

                    if (userByEmail != null)
                        throw new MyExistedException(
                                String.format("User %s already exist", userRequest.getEmail()));

                    user.setEmail(userRequest.getEmail());
                }
            }
        }
    }
}
