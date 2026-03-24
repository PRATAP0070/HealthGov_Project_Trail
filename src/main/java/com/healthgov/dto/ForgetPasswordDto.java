package com.healthgov.dto;

import lombok.Data;

@Data
public class ForgetPasswordDto {
    private String name;       // or phone
    private String password;
}

