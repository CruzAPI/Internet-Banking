package com.cruzapi.internetbanking.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.cruzapi.internetbanking.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name = "transaction_history")
@SuperBuilder
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Transaction implements Serializable
{
	private static final long serialVersionUID = 2520373581529222979L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn
	@JsonIgnore
	private Customer customer;
	
	@Column
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	@Column
	private BigDecimal initialBalance;
	
	@Column
	private BigDecimal amount;
	
	@Column
	private BigDecimal tax;
	
	@Column
	private BigDecimal finalBalance;
	
	@Column
	private ZonedDateTime time;
	
	public String getAccountNumber()
	{
		return customer.getAccountNumber();
	}
}
