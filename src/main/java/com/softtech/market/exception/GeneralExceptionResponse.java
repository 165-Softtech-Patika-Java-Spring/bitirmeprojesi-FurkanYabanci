package com.softtech.market.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class GeneralExceptionResponse {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date errorDate;
    private String message;
    private String detail;
}
