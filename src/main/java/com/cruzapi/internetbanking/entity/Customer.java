package com.cruzapi.internetbanking.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name = "customer")
@SuperBuilder
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Customer implements Serializable
{
	private static final long serialVersionUID = -8539495150748591524L;
	
	@Id
	private String accountNumber;
	
	@Column
	private String name;
	
	@Column
	private boolean exclusivePlan;
	
	@Column
	private BigDecimal balance;
	
	@Column
	private LocalDate birthDate;
	
	@OneToMany(mappedBy = "customer", targetEntity = Transaction.class)
	@JsonIgnore
	private List<Transaction> transactionHistory;
}
