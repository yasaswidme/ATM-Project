package com.ATM.Interfaces;

import com.ATM.CustomerException.InsufficientBalanceException;
import com.ATM.CustomerException.InsufficientMachineBalanceException;
import com.ATM.CustomerException.InvalidAmountException;
import com.ATM.CustomerException.NotAOperatorException;

public interface IATMService {
	
	//To get the user type weather the user is operator or user
	public abstract String getUserType() throws NotAOperatorException;
	
	//To withdraw amount 
	//will throw InvalidAmountException if the amount is not valid denomination
	//will throw InsufficientBalanceException if the customer has insufficient funds in account
	//will throw InsufficientMachineBalanceException if the machine has insifficient amount value
	public abstract double withDrawAmount(double withAmount) 
			throws InvalidAmountException,
			InsufficientBalanceException,
			InsufficientMachineBalanceException;
	
	//To deposit Amount
	public abstract double depositAmount(double dptAmount) throws InvalidAmountException;
	
	// To Check balance
	public abstract double checkBalance();
	
	//Change PIN Number
	public abstract void changePinNumber(int PinNumber);
	
	//To get pinnumber
	public abstract int getPinNumber();
	
	//To get username 
	public abstract String getUserName();
	
	//To get the chances of Pin Number
	
	public abstract int getChances();
	
	//To decrease the number of attempts while entering the wrong pin number
	public abstract void decreaseChances();
	
	//To reset pin chances
	public abstract void resetPinChances();
	
	//To generate the mini statement of an account
	public abstract void generateMiniStatement();
	
	
	
}
