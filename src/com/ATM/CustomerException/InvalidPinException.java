package com.ATM.CustomerException;

public class InvalidPinException extends Exception{
	
	public InvalidPinException(String errorMsg) {
		super(errorMsg);
	}

}
