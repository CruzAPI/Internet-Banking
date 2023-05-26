package com.cruzapi.internetbanking.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.cruzapi.internetbanking.validation.groups.Group1;
import com.cruzapi.internetbanking.validation.groups.Group2;
import com.cruzapi.internetbanking.validation.groups.Group3;
import com.cruzapi.internetbanking.validation.groups.Group4;
import com.cruzapi.internetbanking.validation.groups.Group5;
import com.cruzapi.internetbanking.validation.groups.Group6;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@GroupSequence({CustomerDTO.class, Group1.class, Group2.class, Group3.class, Group4.class, Group5.class, Group6.class})
public class CustomerDTO implements Serializable
{
	private static final long serialVersionUID = 1233908592888679404L;
	
	@NotNull(groups = Group1.class, message = "Customer name must not be null.")
	@NotEmpty(groups = Group2.class, message = "Customer name must not be empty.")
	private String name;
	
	private boolean exclusivePlan;
	
	@NotNull(message = "Customer balance must not be null.")
	@PositiveOrZero(message = "Customer balance must not be negative.")
	private BigDecimal balance;
	
	@NotNull(groups = Group3.class, message = "Customer account number must not be null.")
	@NotEmpty(groups = Group4.class, message = "Customer account number must not be empty.")
	@Pattern(groups = Group5.class, regexp = "\\d*", message = "Customer account number must have only digits.")
	private String accountNumber;
	
	@NotNull(message = "Customer birth date must not be null.")
	@Past(message = "Customer birth date must be in past.")
	private LocalDate birthDate;
}
