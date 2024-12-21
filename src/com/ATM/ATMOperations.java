package com.ATM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.ATM.Cards.AxisDebitCard;
import com.ATM.Cards.HdfcDebitCard;
import com.ATM.Cards.OperatorCard;
import com.ATM.Cards.SBIDebitCard;
import com.ATM.CustomerException.InCorrectPinLimitReachedException;
import com.ATM.CustomerException.InsufficientBalanceException;
import com.ATM.CustomerException.InsufficientMachineBalanceException;
import com.ATM.CustomerException.InvalidAmountException;
import com.ATM.CustomerException.InvalidCardException;
import com.ATM.CustomerException.InvalidPinException;
import com.ATM.CustomerException.NotAOperatorException;
import com.ATM.Interfaces.*;

public class ATMOperations {
	// initial ATM Machine Balance
	public static double ATM_MACHINE_BALANCE = 100000.00;

	// activities performed on the ATM Machine
	public static ArrayList<String> ACTIVITY = new ArrayList<>();

	// database to map card numbers to card object
	public static HashMap<Long, IATMService> dataBase = new HashMap<>();

	// flag to indicate if the ATM machine is on or off.
	public static boolean MACHINE_ON = true;

	// Reference to the current card in use.
	public static IATMService card;

	// validate the inserted card by checking against the database.
	public static IATMService validateCard(long cardNumber) throws InvalidCardException {
		if (dataBase.containsKey(cardNumber)) {
			return dataBase.get(cardNumber);
		} else {
			ACTIVITY.add("Accessed By " + cardNumber + " is not compatible ");
			throw new InvalidCardException("This is Not A valid Card ");
		}
	}

	// Display the Activities Performed on the ATM Machine
	public static void checkATMMAchineActivities() {
		System.out.println("============================== Activities Perfomed ===============");
		for (String activity : ACTIVITY) {
			System.out.println("============================================================");
			System.out.println(activity);
			System.out.println("============================================================");
		}
	}

	// Reset the number of pin attempts for a user.
	public static void resetUserAttempts(IATMService operatorCard) {
		IATMService card = null;
		long number;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Your Card Number : ");
		number = scanner.nextLong();
		try {
			card = validateCard(number);
			card.resetPinChances();// resetting pin attemps for a user specified card.
			ACTIVITY.add("Accessed By " + operatorCard.getUserName() + "to reset number of chances for user");
		} catch (InvalidCardException ive) {
			ive.printStackTrace();
		}
		scanner.close();
	}

	// validate user credentials including PIN.
	public static IATMService validateCredentials(long cardNumber, int pinNumber)
			throws InvalidCardException, InvalidPinException, InCorrectPinLimitReachedException {
		if (dataBase.containsKey(cardNumber)) {
			card = dataBase.get(cardNumber);
		} else {
			throw new InvalidCardException("This card is Not A valid Card");
		}
		try {
			if (card.getUserType().equals("operator")) {
				// operator have a different pin validation process.
				if (card.getPinNumber() != pinNumber) {
					throw new InvalidPinException("Dear Operator. Please Enter Correct Pin Number ");
				} else {
					return card;
				}
			}
		} catch (NotAOperatorException noe) {
			noe.printStackTrace();
		}
		// validate pin and handle incorrect attempts.
		if (card.getChances() <= 0) {
			throw new InCorrectPinLimitReachedException(
					" you have Reached wrong limit of Pin Number, Which is 3 attempts");
		}
		if (card.getPinNumber() != pinNumber) {
			card.decreaseChances();// decrease the number of remaing chances.
			throw new InvalidPinException(" You have entered A wrong pin Number");
		} else {
			return card;
		}
	}

	// validate the amount for withdrawal to ensure sufficient machine balance.
	public static void validateAmount(double amount) throws InsufficientMachineBalanceException {
		if (amount > ATM_MACHINE_BALANCE) {
			throw new InsufficientMachineBalanceException(" Insufficient cash in the Machine ");
		}
	}

	// validate deposit amount to ensure it meets Atm requirements.'
	public static void validateDepositAmount(double amount)
			throws InvalidAmountException, InsufficientMachineBalanceException {
		if (amount % 100 != 0) {
			throw new InvalidAmountException("Please deposit multiples of 100..");
		}
		if (amount + ATM_MACHINE_BALANCE > 200000.0) {
			ACTIVITY.add("Unable to Deposit Cash in the ATM Machine ...");
			throw new InsufficientMachineBalanceException(
					"you can't deposi cash as the limit of the machine is reached..");
		}
	}

	public static void operatorMode(IATMService card) {
		Scanner scanner = new Scanner(System.in);
		double amount;
		boolean flag = true;
		while (flag) {
			System.out.println("Operator Mode : Operator Name " + card.getUserName());
			System.out.println("===========================================================");
			System.out.println();
			System.out.println("||                0. Switch off the machine        ||");
			System.out.println("||                1. Check ATM Machine Balance     ||");
			System.out.println("||                2. Depost Cash in The Machine    ||");
			System.out.println("||                3. Reset The User Pin Attempts   ||");
			System.out.println("||                4. To check activities perfomed in ATM Machine     ||");
			System.out.println("||                5. Exit Operator Mode            ||");
			System.out.println("Enter Your Choice ...");
			int option = scanner.nextInt();
			switch (option) {
			case 0:
				MACHINE_ON = false;
				ACTIVITY.add(
						"Accessed By " + card.getUserName() + " Activity Performed : Switching off the ATM Machine ");
				flag = false;
				break;
			case 1:
				ACTIVITY.add(
						"Accessed By " + card.getUserName() + " Activity Performed :  Checking ATM Machine Balance");
				System.out.println(" The Balance of the ATM Machine is : " + ATM_MACHINE_BALANCE + " IS Available ");
				break;
			case 2:
				System.out.println("Enter The Amount To Deposit ");
				amount = scanner.nextDouble();
				try {
					validateDepositAmount(amount);// validate the Deposit Amount
					ATM_MACHINE_BALANCE += amount;// update ATM Machine Balance
					ACTIVITY.add("Accessed By " + card.getUserName()
							+ " Activity Performed :  Deposit Cah in the ATM Machine");
					System.out.println("===================================================================");
					System.out.println("======================== Cash Added in the ATM Machine =====================");
					System.out.println(
							"=================================================================================");
				} catch (InvalidAmountException | InsufficientMachineBalanceException e) {
					e.printStackTrace();
				}
				break;
			case 3:
				resetUserAttempts(card);// reset user Pin Attempts
				System.out.println("===================================================================");
				System.out.println("======================== User Attempts are Reset =====================");
				System.out.println("========================================================================");
				ACTIVITY.add("Accessed By " + card.getUserName()
						+ " Activity Performed : Resetting the Pin Attempts of user");
				break;
			case 4:
				checkATMMAchineActivities();// Display ATM Activities.
				break;
			case 5:
				flag = false;
				break;
			default:
				System.out.println("You Have Entered A Wrong Option..");
			}
		}
		scanner.close();
	}

	public static void main(String[] args) throws NotAOperatorException {
		dataBase.put(22222222221l, new AxisDebitCard(22222222221l, "Siva", 50000.0, 2222));
		dataBase.put(3333333331l, new HdfcDebitCard(3333333331l, "Remo", 70000.0, 3333));
		dataBase.put(4444444441l, new SBIDebitCard(4444444441l, "nandini", 85000.0, 4444));
		dataBase.put(1111111111l, new OperatorCard(1111111111l, 1111, "operator 1"));
		Scanner scanner = new Scanner(System.in);
		long cardNumber = 0;
		double depositAmount = 0.0;
		double withdrawAmount = 0.0;
		int pin = 0;
		while (MACHINE_ON) {
			System.out.println("Please Enter the Debit Card Number : ");
			cardNumber = scanner.nextLong();
			try {
				System.out.println("Please Enter PIN Number : ");
				pin = scanner.nextInt();
				card = validateCredentials(cardNumber, pin);// validate Card and PIN
				if (card == null) {
					System.out.println("Card Validation Failed ...");
					continue;
				}
				ACTIVITY.add("Accessed By " + card.getUserName() + " Status : Access Approved :");
				if (card.getUserType().equals("operator")) {
					operatorMode(card);
					continue;
				}
				while (true) {
					System.out.println("USER MODE : " + card.getUserName());
					System.out.println("||============================================||");
					System.out.println("||             1. Withdraw Amount             ||");
					System.out.println("||             2. Deposit Amount              ||");
					System.out.println("||             3. Check Balance               ||");
					System.out.println("||             4. Change PIN                  ||");
					System.out.println("||             5. MINI Statement              ||");
					System.out.println("||==========================================  ||");
					System.out.println(" Enter Your Choice ");
					int option = scanner.nextInt();
					try {
						switch (option) {
						case 1:
							System.out.println("Please Enter WithDraw Amount : ");
							withdrawAmount = scanner.nextDouble();
							validateAmount(withdrawAmount);
							card.withDrawAmount(withdrawAmount);
							ATM_MACHINE_BALANCE -= withdrawAmount;
							ACTIVITY.add("Accessed By " + card.getUserName() + " Activity : amount withdraw "
									+ withdrawAmount + " from Machine ");
							break;
						case 2:
							System.out.println("Plase Enter Deposit Amount : ");
							depositAmount = scanner.nextDouble();
							validateDepositAmount(depositAmount);
							card.depositAmount(depositAmount);
							ATM_MACHINE_BALANCE += depositAmount;
							ACTIVITY.add("Accessed By " + card.getUserName() + " Activity : amount Deposit "
									+ depositAmount + " in the " + " Machine ");
							break;
						case 3:
							System.out.println("Your Account Balance is : " + card.checkBalance());
							ACTIVITY.add("Accessed By " + card.getUserName() + " Activity : Checking The Balance ");
							break;
						case 4:
							System.out.println("Enter A new Pin : ");
							pin = scanner.nextInt();
							card.changePinNumber(pin);
							ACTIVITY.add("Accessed By " + card.getUserName() + " Activity : Changed Pin Number ");
							break;
						case 5:
							ACTIVITY.add(
									"Accessed By " + card.getUserName() + " Activity : " + "Generating Mini Statement");
							card.generateMiniStatement();
							break;
						default:
							System.out.println("You Have Entered A Wrong Option ");
							break;
						}
						System.out.println("Do You Want To Continue ...");
						String nextOption = scanner.next();
						if (nextOption.equalsIgnoreCase("n")) {
							break;// exit user Mode
						}
					} catch (InvalidAmountException | InsufficientBalanceException
							| InsufficientMachineBalanceException e) {
						e.printStackTrace();
					}
				}
			} catch (InvalidPinException | InvalidCardException | InCorrectPinLimitReachedException e) {
				ACTIVITY.add("Accessed By " + card.getUserName() + " Status : Accessed Denied ");
				e.printStackTrace();
			}
		}System.out.println("=========================================================");
			System.out.println("===============Thanks For Using ICICI ATM Machine==================");
			System.out.println("=======================================================");
			scanner.close();
		
	}

}
