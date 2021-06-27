package com.philconnal.shoezone.controller.request;

import com.philconnal.shoezone.util.ParamError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthUserRequest {
    @NotBlank(message = ParamError.FIELD_NAME)
    private String username;
    @NotBlank(message = ParamError.FIELD_NAME)
    private String password;

}
