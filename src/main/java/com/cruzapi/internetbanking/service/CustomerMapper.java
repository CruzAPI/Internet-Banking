package com.cruzapi.internetbanking.service;

import org.springframework.stereotype.Service;

import com.cruzapi.internetbanking.dto.CustomerDTO;
import com.cruzapi.internetbanking.entity.Customer;

@Service
public class CustomerMapper
{
	public Customer toEntity(CustomerDTO customer)
	{
		return Customer.builder()
				.name(customer.getName())
				.exclusivePlan(customer.isExclusivePlan())
				.balance(customer.getBalance())
				.accountNumber(customer.getAccountNumber())
				.birthDate(customer.getBirthDate())
				.build();
	}
}
