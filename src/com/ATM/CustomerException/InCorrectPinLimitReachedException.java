package com.ATM.CustomerException;

public class InCorrectPinLimitReachedException extends Exception{
	
	public InCorrectPinLimitReachedException(String errorMsg) {
		super(errorMsg);
	}

}
