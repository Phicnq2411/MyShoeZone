package com.philconnal.shoezone.controller.request;


import com.philconnal.shoezone.common.enums.ProductType;
import com.philconnal.shoezone.util.ParamError;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateProductRequest {
    @NotBlank(message = ParamError.FIELD_NAME)
    private String name;

    @NotNull(message = ParamError.FIELD_NAME)
    private Integer size;

    @NotNull(message = ParamError.FIELD_NAME)
    private Integer type;

    @NotNull(message = ParamError.FIELD_NAME)
    private Integer status;

    @NotNull(message = ParamError.FIELD_NAME)
    private Long branchID;

    @NotNull(message = ParamError.FIELD_NAME)
    private Double price;

    @NotBlank(message = ParamError.FIELD_NAME)
    private String imgUrl;

    @NotBlank(message = ParamError.FIELD_NAME)
    private String description;
}
