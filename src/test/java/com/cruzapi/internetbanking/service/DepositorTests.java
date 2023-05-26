package com.cruzapi.internetbanking.service;

import static com.cruzapi.internetbanking.utility.CustomerUtil.getCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.cruzapi.internetbanking.entity.Customer;
import com.cruzapi.internetbanking.exception.InvalidDepositValueException;

class DepositorTests
{
	private Depositor depositor;
	
	private TransactionService transactionLogger;
	
	@BeforeEach
	void beforeEach()
	{
		transactionLogger = mock(TransactionService.class);
		
		depositor = new Depositor(transactionLogger);
	}
	
	@ParameterizedTest
	@MethodSource("notPositiveDepositValuesParameters")
	void testNotPositiveDepositValues(BigDecimal depositValue)
	{
		Exception e = assertThrows(InvalidDepositValueException.class, () -> depositor.deposit(null, depositValue));
		
		assertEquals("Deposit value must be positive.", e.getMessage());
	}
	
	@ParameterizedTest
	@MethodSource("depositParameters")
	void testDeposit(Customer customer, BigDecimal depositValue, BigDecimal expectedBalance)
	{
		depositor.deposit(customer, depositValue);
		
		assertThat(customer.getBalance()).isEqualByComparingTo(expectedBalance);
	}
	
	private static Stream<Arguments> notPositiveDepositValuesParameters()
	{
		return Stream.of(
				Arguments.of(new BigDecimal("0.0")),
				Arguments.of(new BigDecimal("+0.0")),
				Arguments.of(new BigDecimal("-0.0")),
				Arguments.of(new BigDecimal("-0.00000001")),
				Arguments.of(new BigDecimal("-10.0")),
				Arguments.of(new BigDecimal(Long.MIN_VALUE)),
				Arguments.of(new BigDecimal(Integer.MIN_VALUE)),
				Arguments.of(new BigDecimal("-9999999999.9"))
				);
	}
	
	private static Stream<Arguments> depositParameters()
	{
		return Stream.of(
				Arguments.of(getCustomer("0.0"), new BigDecimal("10.0"), new BigDecimal("10.0")),
				Arguments.of(getCustomer("50.0"), new BigDecimal("10.0"), new BigDecimal("60.0")),
				Arguments.of(getCustomer("1555.0"), new BigDecimal("0.5"), new BigDecimal("1555.5")),
				Arguments.of(getCustomer("-10.0"), new BigDecimal("10.0"), new BigDecimal("0.0")),
				Arguments.of(getCustomer("0.1"), new BigDecimal("9999999999.9"), new BigDecimal("10000000000.0")),
				Arguments.of(getCustomer("0.00001"), new BigDecimal("0.00001"), new BigDecimal("0.00002"))
				);
	}
}
