package com.softtech.market.exception;

import com.softtech.market.dto.GeneralResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest webRequest){
        String message = ex.getMessage();
        String description = webRequest.getDescription(false);
        Date errorDate = new Date();
        GeneralExceptionResponse generalExceptionResponse = new GeneralExceptionResponse(errorDate, message, description);
        GeneralResponseDto<GeneralExceptionResponse> generalResponse = GeneralResponseDto.error(generalExceptionResponse);
        generalResponse.setMessage(message);

        return new ResponseEntity<>(generalResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleAllItemNotFoundException(ItemNotFoundException ex, WebRequest webRequest){
        String message = ex.getMessage();
        String description = webRequest.getDescription(false);
        Date errorDate = new Date();

        GeneralExceptionResponse generalExceptionResponse = new GeneralExceptionResponse(errorDate, message, description);
        GeneralResponseDto<GeneralExceptionResponse> generalResponse = GeneralResponseDto.error(generalExceptionResponse);
        generalResponse.setMessage(message);

        return new ResponseEntity<>(generalResponse, HttpStatus.NOT_FOUND);
    }
}
