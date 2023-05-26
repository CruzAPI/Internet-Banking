package com.cruzapi.internetbanking.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionDTO
{
	@NotBlank(message = "Account number field must not be blank")
	private String accountNumber;
	
	@NotNull(message = "Amount field must not be null")
	private BigDecimal amount;
}
