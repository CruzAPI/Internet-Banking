package com.cruzapi.internetbanking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cruzapi.internetbanking.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String>
{
	
}
