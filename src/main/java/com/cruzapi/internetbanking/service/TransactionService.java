package com.cruzapi.internetbanking.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cruzapi.internetbanking.entity.Customer;
import com.cruzapi.internetbanking.entity.Transaction;
import com.cruzapi.internetbanking.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService
{
	private final TransactionRepository repository;
	
	public Transaction log(Transaction transaction)
	{
		return repository.save(transaction);
	}
	
	public Page<Transaction> findAllByCustomer(Customer customer, Pageable pageable)
	{
		return repository.findAllByCustomer(customer, pageable);
	}
	
	public List<Transaction> findAllByTimeBetween(LocalDate initialDate, LocalDate finalDate)
	{
		ZonedDateTime start = ZonedDateTime.of(initialDate, LocalTime.MIN, ZoneId.systemDefault());
		ZonedDateTime end = ZonedDateTime.of(finalDate, LocalTime.MAX, ZoneId.systemDefault());
		
		return repository.findAllByTimeBetween(start, end);
	}
}
