package com.cruzapi.internetbanking.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cruzapi.internetbanking.entity.Customer;
import com.cruzapi.internetbanking.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>
{
	Page<Transaction> findAllByCustomer(Customer customer, Pageable pageable);
	List<Transaction> findAllByTimeBetween(ZonedDateTime start, ZonedDateTime end);
}
