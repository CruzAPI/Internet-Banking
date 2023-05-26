package com.cruzapi.internetbanking.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cruzapi.internetbanking.dto.CustomerDTO;
import com.cruzapi.internetbanking.entity.Customer;
import com.cruzapi.internetbanking.service.CustomerMapper;
import com.cruzapi.internetbanking.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController
{
	private final CustomerMapper customerMapper;
	private final CustomerService customerService;
	
	@GetMapping
	public ResponseEntity<Page<Customer>> listCustomers(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int pageSize)
	{
		Page<Customer> customers = customerService.findAll(PageRequest.of(page, pageSize));
		
		if(customers.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(customers);
	}
	
	@PostMapping
	public ResponseEntity<?> registerCustomer(@RequestBody @Valid CustomerDTO customerDTO)
	{
		Customer customer = customerMapper.toEntity(customerDTO);
		
		if(customerService.isRegistered(customer))
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).body(
					String.format("Customer with account number \"%s\" already registered.", 
							customer.getAccountNumber()));
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(customerService.register(customer));
	}
}
