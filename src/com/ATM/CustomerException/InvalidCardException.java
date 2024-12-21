package com.ATM.CustomerException;

public class InvalidCardException extends Exception {
	
	public InvalidCardException(String errorMsg) {
		super(errorMsg);
	}
}
