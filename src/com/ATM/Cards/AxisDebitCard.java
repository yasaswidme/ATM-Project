package com.ATM.Cards;
import java.util.ArrayList;
import java.util.Collections;
import com.ATM.CustomerException.InsufficientBalanceException;
import com.ATM.CustomerException.InsufficientMachineBalanceException;
import com.ATM.CustomerException.InvalidAmountException;
import com.ATM.CustomerException.NotAOperatorException;
import com.ATM.Interfaces.IATMService;

public class AxisDebitCard implements IATMService {
	String name;
	long debiCardNumber;
	double accountBalance;
	int pinNumber;
	ArrayList<String> statement;
	final String type = "user";
	int chances;
	
	//constructor
	public AxisDebitCard(long debitCardNumber, String name, double accountBalance, int pinNumber) {
		chances = 3;
		this.name = name;
		this.accountBalance = accountBalance;
		this.pinNumber = pinNumber;
		statement = new ArrayList<>();
	}
	@Override
	public String getUserType() throws NotAOperatorException {
		return type;
	}

	@Override
	public double withDrawAmount(double withAmount)
			throws InvalidAmountException,
			InsufficientBalanceException,
			InsufficientMachineBalanceException {
		if(withAmount<=0) {
			throw new InvalidAmountException("You have entered zero, please enter amount greater the zero ");
		}else if(withAmount%100!=0){
			throw new InvalidAmountException("Please Withdraw Multiples Of 100 only");
		}else if(withAmount<500) {
			throw new InvalidAmountException("Please Withdraw Amount More Than 500");
		}else if(withAmount>accountBalance) {
			throw new InsufficientBalanceException("You have Insufficient Funds In Accoount");
		}else {
			accountBalance = accountBalance - withAmount;
			statement.add("Debited : " + withAmount);
			return withAmount;
		}
		
	}

	@Override
	public double depositAmount(double dptAmount) throws InvalidAmountException {
		if(dptAmount<=500) {
			throw new InvalidAmountException("You Can't Deposit Zero or Lessthan Zero Deposit Amount Must Be Greater Than 500");
		}else if(dptAmount%100!=0) {
			throw new InvalidAmountException("Please Deposit Multiples Of 100");
		}else {
			accountBalance = accountBalance + dptAmount;
			statement.add("Credited : " + dptAmount);
			return dptAmount;
		}
	}

	@Override
	public double checkBalance() {
		return accountBalance;
	}

	@Override
	public void changePinNumber(int pinNumber) {
		this.pinNumber = pinNumber;
		
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
		return chances;
	}

	@Override
	public void decreaseChances() {
		--chances;
	}

	@Override
	public void resetPinChances() {
		chances = 3;
	}

	@Override
	public void generateMiniStatement() {
		int count = 5;
		if(statement.size() == 0) {
			System.out.println("No Transcations Happened");
		}
		System.out.println("=========== Last 5 Transcations ===========");
		Collections.reverse(statement);
		for(String trans : statement) {
			if(count == 0) {
				break;
			}
			System.out.println(trans);
			count--;
		}
		Collections.reverse(statement);
	}
	
}
