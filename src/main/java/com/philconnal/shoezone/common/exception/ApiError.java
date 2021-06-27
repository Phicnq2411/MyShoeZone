package com.philconnal.shoezone.common.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class ApiError implements Serializable {
    private int error;
    private HttpStatus status;
    private String message;
    private String description;
    private ZonedDateTime timestamp;


}
