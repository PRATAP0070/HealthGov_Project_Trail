package com.healthgov.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramManagerDecisionRequest {

    @NotBlank(message = "decision is required")
    private String decision;  

    private String reason;   

    private Double amount;
}