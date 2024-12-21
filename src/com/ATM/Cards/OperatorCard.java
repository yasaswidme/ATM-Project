package com.ATM.Cards;

import com.ATM.CustomerException.InsufficientBalanceException;
import com.ATM.CustomerException.InsufficientMachineBalanceException;
import com.ATM.CustomerException.InvalidAmountException;
import com.ATM.CustomerException.NotAOperatorException;
import com.ATM.Interfaces.IATMService;

public class OperatorCard implements IATMService {
	
	private int pinNumber;
	private long id;
	private String name;
	private final String type = "operator";
	
	public OperatorCard(long id, int pin, String name) {
		id = id;
		pinNumber = pin;
		this.name = name;
	}
	
	@Override
	public String getUserType() throws NotAOperatorException {	
		return type;
	}

	@Override
	public double withDrawAmount(double withAmount)
			throws InvalidAmountException, InsufficientBalanceException, InsufficientMachineBalanceException {
		return 0;
	}

	@Override
	public double depositAmount(double dptAmount) throws InvalidAmountException {
		return 0;
	}

	@Override
	public double checkBalance() {
		return 0;
	}

	@Override
	public void changePinNumber(int PinNumber) {
		
	}

	@Override
	public int getPinNumber() {
		return pinNumber;
	}

	@Override
	public String getUserName() {
		return name;
	}

	@Override
	public int getChances() {
		return 0;
	}

	@Override
	public void decreaseChances() {
		
	}

	@Override
	public void resetPinChances() {
		
	}

	@Override
	public void generateMiniStatement() {
	
	}
	
	
}
