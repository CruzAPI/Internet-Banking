package com.cruzapi.internetbanking.service;

import static com.cruzapi.internetbanking.utility.CustomerUtil.getCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.cruzapi.internetbanking.entity.Customer;
import com.cruzapi.internetbanking.exception.InsufficientBalanceException;
import com.cruzapi.internetbanking.exception.InvalidWithdrawalValueException;

class WithdrawerTests
{
	private Withdrawer withdrawer;
	
	private TransactionService transactionLogger;
	
	@BeforeEach
	void beforeEach()
	{
		transactionLogger = mock(TransactionService.class);
		
		withdrawer = new Withdrawer(transactionLogger);
	}
	
	@ParameterizedTest
	@MethodSource("negativeWithdrawalValuesParameters")
	void testNegativeWithdrawalValues(BigDecimal withdrawalValue)
	{
		Exception e = assertThrows(InvalidWithdrawalValueException.class, () -> withdrawer.withdraw(null, withdrawalValue));
		
		assertEquals("Withdrawal value must be positive.", e.getMessage());
	}
	
	@ParameterizedTest
	@MethodSource("withdrawWithInsufficientBalanceParameters")
	void testWithdrawWithInsufficientBalance(Customer customer, BigDecimal withdrawalValue)
	{
		BigDecimal currentBalance = customer.getBalance();
		
		assertThrows(InsufficientBalanceException.class, () -> withdrawer.withdraw(customer, withdrawalValue));
		assertSame(currentBalance, customer.getBalance());
	}
	
	@ParameterizedTest
	@MethodSource("withdrawParameters")
	void testWithdraw(Customer customer, BigDecimal withdrawalValue, BigDecimal expectedBalance)
	{
		withdrawer.withdraw(customer, withdrawalValue);
		
		assertThat(customer.getBalance()).isEqualByComparingTo(expectedBalance);
	}
	
	private static Stream<Arguments> negativeWithdrawalValuesParameters()
	{
		return Stream.of(
				Arguments.of(new BigDecimal("0.0")),
				Arguments.of(new BigDecimal("-1.0")),
				Arguments.of(new BigDecimal("-250.0")),
				Arguments.of(new BigDecimal(Long.MIN_VALUE))
				);
	}
	
	private static Stream<Arguments> withdrawWithInsufficientBalanceParameters()
	{
		return Stream.of(
				Arguments.of(getCustomer("1000.0"), new BigDecimal("10000.0")),
				Arguments.of(getCustomer("0.0"), new BigDecimal("0.01")),
				Arguments.of(getCustomer("1000.0"), new BigDecimal("1000.00000001")),
				Arguments.of(getCustomer("999.99999"), new BigDecimal("999.999999")),
				Arguments.of(getCustomer("1000.0"), new BigDecimal("9999999999.999999"))
				);
	}
	
	private static Stream<Arguments> withdrawParameters()
	{
		return Stream.of(
				Arguments.of(getCustomer("66.666"), new BigDecimal("66.666"), new BigDecimal("0.0")),
				Arguments.of(getCustomer("999.999", true), new BigDecimal("999.999"), new BigDecimal("0.0")),
				Arguments.of(getCustomer("1000.0"), new BigDecimal("1.0"), new BigDecimal("999.0")),
				Arguments.of(getCustomer("1000.0"), new BigDecimal("2.0"), new BigDecimal("998.0")),
				Arguments.of(getCustomer("1000.0"), new BigDecimal("300.0"), new BigDecimal("698.8")),
				Arguments.of(getCustomer("1000.0"), new BigDecimal("300.1"), new BigDecimal("696.899")),
				Arguments.of(getCustomer("1000.0", true), new BigDecimal("300.1"), new BigDecimal("699.9")),
				Arguments.of(getCustomer("1000.0", true), new BigDecimal("300.0"), new BigDecimal("700.0")),
				Arguments.of(getCustomer("1000.0"), new BigDecimal("500.0"), new BigDecimal("495.0")),
				Arguments.of(getCustomer("1000.0", true), new BigDecimal("500.0"), new BigDecimal("500.0"))
				);
	}
}
