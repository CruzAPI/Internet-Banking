package com.cruzapi.internetbanking.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cruzapi.internetbanking.dto.TransactionDTO;
import com.cruzapi.internetbanking.entity.Customer;
import com.cruzapi.internetbanking.entity.Transaction;
import com.cruzapi.internetbanking.exception.InsufficientBalanceException;
import com.cruzapi.internetbanking.exception.InvalidDepositValueException;
import com.cruzapi.internetbanking.exception.InvalidWithdrawalValueException;
import com.cruzapi.internetbanking.service.CustomerService;
import com.cruzapi.internetbanking.service.Depositor;
import com.cruzapi.internetbanking.service.TransactionService;
import com.cruzapi.internetbanking.service.Withdrawer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController
{
	private final Depositor depositor;
	private final Withdrawer withdrawer;
	private final CustomerService customerService;
	private final TransactionService transactionService;
	
	@PatchMapping("/deposit")
	public ResponseEntity<Transaction> deposit(@RequestBody @Valid TransactionDTO transactionDTO)
	{
		final String accountNumber = transactionDTO.getAccountNumber();
		Optional<Customer> optional = customerService.findByAccountNumber(accountNumber);
		
		if(optional.isEmpty())
		{
			return ResponseEntity.notFound().build();
		}
		
		Transaction transaction = depositor.deposit(optional.get(), transactionDTO.getAmount());
		
		return ResponseEntity.ok(transaction);
	}
	
	@PatchMapping("/withdraw")
	public ResponseEntity<Transaction> withdraw(@RequestBody TransactionDTO transactionDTO)
	{
		Optional<Customer> optional = customerService.findByAccountNumber(transactionDTO.getAccountNumber());
		
		if(optional.isEmpty())
		{
			return ResponseEntity.notFound().build();
		}
		
		Transaction transaction = withdrawer.withdraw(optional.get(), transactionDTO.getAmount());
		
		return ResponseEntity.ok(transaction);
	}
	
	@GetMapping
	public ResponseEntity<List<Transaction>> getTransactions(
			@RequestParam LocalDate initialDate, 
			@RequestParam LocalDate finalDate)
	{
		List<Transaction> transactions = transactionService.findAllByTimeBetween(initialDate, finalDate);
		
		if(transactions.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(transactions);
	}
	
	@ExceptionHandler({InvalidDepositValueException.class, InvalidWithdrawalValueException.class, InsufficientBalanceException.class})
	public ResponseEntity<String> handleInvalidDepositValue(Exception e) 
	{
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
