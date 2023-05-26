package com.cruzapi.internetbanking.service;

import static com.cruzapi.internetbanking.enums.TransactionType.DEPOSIT;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Service;

import com.cruzapi.internetbanking.entity.Customer;
import com.cruzapi.internetbanking.entity.Transaction;
import com.cruzapi.internetbanking.exception.InvalidDepositValueException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Depositor
{
	private final TransactionService transactionLogger;
	
	public Transaction deposit(final Customer customer, final BigDecimal depositValue)
	{
		if(depositValue.compareTo(BigDecimal.ZERO) < 1)
		{
			throw new InvalidDepositValueException("Deposit value must be positive.");
		}
		
		BigDecimal initialBalance = customer.getBalance();
		BigDecimal finalBalance = initialBalance.add(depositValue);
		
		customer.setBalance(finalBalance);
		
		return transactionLogger.log(Transaction.builder()
				.customer(customer)
				.transactionType(DEPOSIT)
				.initialBalance(initialBalance)
				.amount(depositValue)
				.finalBalance(finalBalance)
				.time(ZonedDateTime.now())
				.build());
	}
}
