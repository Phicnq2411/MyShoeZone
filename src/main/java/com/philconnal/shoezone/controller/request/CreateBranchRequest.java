package com.philconnal.shoezone.controller.request;


import com.philconnal.shoezone.util.ParamError;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CreateBranchRequest {
    @NotBlank(message = ParamError.FIELD_NAME)
    private String name;

    @NotBlank(message = ParamError.FIELD_NAME)

    private String address;
}
