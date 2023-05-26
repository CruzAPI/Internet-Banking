package com.cruzapi.internetbanking.utility;

import java.math.BigDecimal;

import com.cruzapi.internetbanking.entity.Customer;

public class CustomerUtil
{
	public static Customer getCustomer(String balance)
	{
		return getCustomer(balance, false);
	}
	
	public static Customer getCustomer(String balance, boolean exclusivePlan)
	{
		return getCustomer(new BigDecimal(balance), exclusivePlan);
	}
	
	public static Customer getCustomer(BigDecimal balance, boolean exclusivePlan)
	{
		Customer customer = new Customer();
		customer.setBalance(balance);
		customer.setExclusivePlan(exclusivePlan);
		return customer;
	}
}
