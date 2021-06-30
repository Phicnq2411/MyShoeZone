package com.philconnal.shoezone.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.philconnal.shoezone.util.Constant;
import com.philconnal.shoezone.util.ParamError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewerRequest {
    @NotBlank(message = ParamError.FIELD_NAME)
    private String username;
    @NotBlank(message = ParamError.FIELD_NAME)
    private String email;
    @NotBlank(message = ParamError.FIELD_NAME)
    private String password;
    @NotBlank(message = ParamError.FIELD_NAME)
    private String first_name;
    @NotBlank(message = ParamError.FIELD_NAME)
    private String last_name;
    @NotBlank(message = ParamError.FIELD_NAME)
    private String phone;
    @NotBlank(message = ParamError.FIELD_NAME)
    private String address;
    @NotBlank(message = ParamError.FIELD_NAME)
    private String confirmed;
    @NotBlank(message = ParamError.FIELD_NAME)
    private String dob;
}
