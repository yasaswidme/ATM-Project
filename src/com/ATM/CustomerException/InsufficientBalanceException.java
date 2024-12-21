package com.ATM.CustomerException;

public class InsufficientBalanceException extends Exception {
	public InsufficientBalanceException(String errorMsg) {
		super(errorMsg);
	}
}
