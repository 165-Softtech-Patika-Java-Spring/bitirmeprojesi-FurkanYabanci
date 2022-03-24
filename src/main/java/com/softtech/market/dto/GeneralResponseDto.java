package com.softtech.market.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralResponseDto<T> implements Serializable {

    private T data;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date responseDate;
    private boolean isSuccess;
    private String message;

    public GeneralResponseDto(T data, boolean isSuccess) {
        this.data = data;
        this.isSuccess = isSuccess;
        responseDate = new Date();
    }
    public GeneralResponseDto(T data, boolean isSuccess, String message) {
        this.data = data;
        this.isSuccess = isSuccess;
        this.message = message;
        responseDate = new Date();
    }

    public static <T> GeneralResponseDto<T> of(T t){
        return new GeneralResponseDto<>(t, true);
    }
    public static <T> GeneralResponseDto<T> of(String message){
        return new GeneralResponseDto<>(null, true, message);
    }
    public static <T> GeneralResponseDto<T> error(T t){
        return new GeneralResponseDto<>(t, false);
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
