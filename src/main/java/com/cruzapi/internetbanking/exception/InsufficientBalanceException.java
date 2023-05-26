package com.cruzapi.internetbanking.exception;

import lombok.experimental.StandardException;

@StandardException
public class InsufficientBalanceException extends RuntimeException	
{
	private static final long serialVersionUID = -1900652264434654408L;
}
