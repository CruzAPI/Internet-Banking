package com.cruzapi.internetbanking.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cruzapi.internetbanking.entity.Customer;
import com.cruzapi.internetbanking.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService
{
	private final CustomerRepository customerRepository;
	
	public Customer register(Customer customer)
	{
		return customerRepository.save(customer);
	}
	
	public boolean isRegistered(Customer customer)
	{
		return customerRepository.findById(customer.getAccountNumber()).isPresent();
	}
	
	public Optional<Customer> findByAccountNumber(String accountNumber)
	{
		return customerRepository.findById(accountNumber);	
	}
	
	public Page<Customer> findAll(Pageable pageable)
	{
		return customerRepository.findAll(pageable);
	}
}
