package com.ATM.CustomerException;

public class InsufficientMachineBalanceException extends Exception {
	
	public InsufficientMachineBalanceException(String errorMsg) {
		super(errorMsg);
	}
}
