package com.cruzapi.internetbanking.service;

import static com.cruzapi.internetbanking.enums.TransactionType.WITHDRAW;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Service;

import com.cruzapi.internetbanking.entity.Customer;
import com.cruzapi.internetbanking.entity.Transaction;
import com.cruzapi.internetbanking.exception.InsufficientBalanceException;
import com.cruzapi.internetbanking.exception.InvalidWithdrawalValueException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Withdrawer
{
	private final TransactionService transactionLogger;
	
	public Transaction withdraw(final Customer customer, final BigDecimal withdrawalValue)
	{
		if(withdrawalValue.compareTo(BigDecimal.ZERO) < 1)
		{
			throw new InvalidWithdrawalValueException("Withdrawal value must be positive.");
		}
		
		BigDecimal initialBalance = customer.getBalance();
		BigDecimal taxMultiplier = getTaxMultiplier(customer, withdrawalValue);
		BigDecimal tax = withdrawalValue.multiply(taxMultiplier);
		BigDecimal withdrawalValueWithFees = withdrawalValue.add(tax);
		BigDecimal finalBalance = initialBalance.subtract(withdrawalValueWithFees);
		
		if(finalBalance.compareTo(BigDecimal.ZERO) == -1)
		{
			throw new InsufficientBalanceException("Insufficient balance.");
		}
		
		customer.setBalance(finalBalance);
		
		return transactionLogger.log(Transaction.builder()
				.customer(customer)
				.transactionType(WITHDRAW)
				.initialBalance(initialBalance)
				.amount(withdrawalValue)
				.tax(tax)
				.finalBalance(finalBalance)
				.time(ZonedDateTime.now())
				.build());
	}
	
	private BigDecimal getTaxMultiplier(final Customer customer, final BigDecimal withdrawalValue)
	{
		if(withdrawalValue.compareTo(new BigDecimal("100.0")) < 1 || customer.isExclusivePlan())
		{
			return BigDecimal.ZERO;
		}
		
		if(withdrawalValue.compareTo(new BigDecimal("300.0")) < 1)
		{
			return new BigDecimal("0.004");
		}
		
		return new BigDecimal("0.01");
	}
}
