package com.ATM.CustomerException;

public class InvalidAmountException extends Exception{
	
	public InvalidAmountException(String errorMsg) {
		super(errorMsg);
	}

}
