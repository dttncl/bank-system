package client;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import bus.*;

public class BankApplication {

	public static void main(String[] args) throws InsufficientFundsException, ClassNotFoundException, SQLException, IOException {

		Scanner in = new Scanner(System.in);

		System.out.println("\n+++++++++ SIGN IN AS A CUSTOMER +++++++++\n");
		CustomerUI();

		in.close();
		
	}

	public static void CustomerUI() throws SQLException, InsufficientFundsException, IOException, ClassNotFoundException {
		Scanner in = new Scanner(System.in);
		boolean init = false;
		ArrayList<ITransaction> listConsoleTransactions = new ArrayList<ITransaction>();
		String id;
		String pin;
		int aOpt = 0;

		ArrayList<ICustomer> listConsoleCustomers = CustomersList.readFromFile();

		if (listConsoleCustomers.size() > 0) {
			CustomersList.setListCustomers(listConsoleCustomers);
		} 
		
		System.out.print("Enter your CustomerID: ");
		id = in.next();
		System.out.print("Enter your PIN: ");
		pin = in.next();
		
		ICustomer currentCustomer = CustomersList.search(id, pin);

		if (currentCustomer != null) {
			
			ICustomer modifiedCustomer = CustomersList.search(id, pin);
			
			ArrayList<ICustomer> listCustomers = CustomersList.getListCustomers();
			listCustomers.remove(modifiedCustomer);
			

			do {
				try {
					
					if (init == true) {
						CustomersList.writeToFile();
					}
					
					listConsoleCustomers = CustomersList.readFromFile();

					if (listConsoleCustomers.size() > 0) {
						CustomersList.setListCustomers(listConsoleCustomers);
					} 
					
					
					System.out.print("\n\n----- CHOOSE ACCOUNT -----\n");
					System.out.println("[1] Checking");
					System.out.println("[2] Savings");
					System.out.println("[3] Currency");
					System.out.println("[4] Line Of Credit");
					System.out.println("[5] Credit");
					System.out.println("[0] Save And Log Out");
					System.out.print("Enter Choice: ");
					aOpt = Integer.valueOf(in.next());
					while (aOpt < 0 || aOpt > 5) {
						System.out.print(" Enter valid option: ");
						aOpt = Integer.valueOf(in.next());
					}

					IAccount currentAccount = null;

					switch(aOpt) {
					case 1:
						currentAccount = currentCustomer.searchAccount(EnumAccount.CheckingAccount);
						if (currentAccount == null) {
							System.out.println("Account Not Found");
						} else {
							startTransaction(currentAccount, currentCustomer, listConsoleTransactions);
						}
						break;
					case 2:
						currentAccount = currentCustomer.searchAccount(EnumAccount.SavingsAccount);
						if (currentAccount == null) {
							System.out.println("Account Not Found");
						} else {
							startTransaction(currentAccount, currentCustomer, listConsoleTransactions);
						}
						break;
					case 3:
						currentAccount = currentCustomer.searchAccount(EnumAccount.CurrencyAccount);
						if (currentAccount == null) {
							System.out.println("Account Not Found");
						} else {
							startTransaction(currentAccount, currentCustomer, listConsoleTransactions);
						}
						break;
					case 4:
						currentAccount = currentCustomer.searchAccount(EnumAccount.LineOfCreditAccount);
						if (currentAccount == null) {
							System.out.println("Account Not Found");
						} else {
							startTransaction(currentAccount, currentCustomer, listConsoleTransactions);
						}
						break;
					case 5:
						currentAccount = currentCustomer.searchAccount(EnumAccount.CreditAccount);
						if (currentAccount == null) {
							System.out.println("Account Not Found");
						} else {
							startTransaction(currentAccount, currentCustomer, listConsoleTransactions);
						}
						break;
					case 0:
						System.out.println("Saving Changes And Logging Out . . .");
						
						listCustomers.add(currentCustomer);
						CustomersList.setListCustomers(listCustomers);
						CustomersList.writeToFile();
						
						break;
					}

				} catch (NumberFormatException ex) {
					System.out.println("Invalid Input received. Please try again!");
					aOpt = 5;
					continue;
				}

				init = true;
				
			} while (aOpt < 6 && aOpt > 0);
			


		} else {
			System.out.println("Customer Not Found!");
		}
	}	

	public static void startTransaction(IAccount currentAccount, ICustomer currentCustomer, ArrayList<ITransaction> listConsoleTransactions) throws SQLException, InsufficientFundsException {
		int tOpt = 0;
		double amount;
		Scanner in = new Scanner(System.in);

		do {
			try {
				System.out.print("\n\n----- CHOOSE TRANSACTION -----\n");
				System.out.println("[1] Withdraw");
				System.out.println("[2] Deposit");
				System.out.println("[3] Check Balance");
				System.out.println("[0] Cancel");
				System.out.print("Enter Choice: ");
				tOpt = Integer.valueOf(in.next());
				while (tOpt < 0 || tOpt > 3) {
					System.out.print(" Enter valid option: ");
					tOpt = Integer.valueOf(in.next());
				}

				ITransaction newTransaction = new Transaction();
				newTransaction.setAccountNumber(currentAccount.getAccountNumber());

				int cpt = 0;
				double transacFee = 0;
				if (currentAccount.getType() == EnumAccount.CheckingAccount) {
					for (ITransaction oneTrans : listConsoleTransactions)
					{
						if (oneTrans.getAccountNumber() == currentAccount.getAccountNumber()) {
							cpt++;
						}
					}

					if (cpt > ((CheckingAccount) currentAccount).getFreeTransactions()) {
						transacFee = ((CheckingAccount) currentAccount).getTransactionFee();
					}
				}


				switch(tOpt) {
				case 1:
					try {
						
						IAccount modifiedAcc = AccountsList.searchById(currentAccount.getAccountNumber());
						ArrayList<IAccount> listCustomerAccounts = currentCustomer.getListOfAccounts();
						listCustomerAccounts.remove(modifiedAcc);
						
						newTransaction.setTransactionType(EnumTransaction.Withdraw);

						System.out.print("\n\n----- ENTER AMOUNT -----\n");
						System.out.print("Enter Amount (in CAD): $");
						amount = in.nextDouble();
						newTransaction.setAmount(amount);

						currentAccount.addTransaction(newTransaction, transacFee);
						listConsoleTransactions.add(newTransaction);
						
						listCustomerAccounts.add(modifiedAcc);
						currentCustomer.setListOfAccounts(listCustomerAccounts);
						
						System.out.println(currentCustomer + "\n\n" + currentAccount + "\n\n" + newTransaction);

					} catch (InsufficientFundsException ex) {
						System.out.println(ex.getMessage());
					}
					break;
				case 2:
					
					IAccount modifiedAcc2 = AccountsList.searchById(currentAccount.getAccountNumber());
					ArrayList<IAccount> listCustomerAccounts2 = currentCustomer.getListOfAccounts();
					listCustomerAccounts2.remove(modifiedAcc2);
					
					
					newTransaction.setTransactionType(EnumTransaction.Deposit);

					System.out.print("\n\n----- ENTER AMOUNT -----\n");
					System.out.print("Enter Amount (in CAD): $");
					amount = in.nextDouble() - transacFee;
					newTransaction.setAmount(amount);

					if (currentAccount.getType() == EnumAccount.LineOfCreditAccount || currentAccount.getType() == EnumAccount.CreditAccount) {
						double bal = ((LineOfCreditAccount) currentAccount).getCurrentBalance();
						if (bal < amount) {
							((LineOfCreditAccount) currentAccount).setCurrentBalance(0);
						} else {
							((LineOfCreditAccount) currentAccount).setCurrentBalance(bal - amount);
						}
					}

					currentAccount.addTransaction(newTransaction, transacFee);
					listConsoleTransactions.add(newTransaction);

					
					listCustomerAccounts2.add(modifiedAcc2);
					currentCustomer.setListOfAccounts(listCustomerAccounts2);
					
					System.out.println(currentCustomer + "\n\n" + currentAccount + "\n\n" + newTransaction);

					break;
				case 3:
					IAccount modifiedAcc3 = AccountsList.searchById(currentAccount.getAccountNumber());
					ArrayList<IAccount> listCustomerAccounts3 = currentCustomer.getListOfAccounts();
					listCustomerAccounts3.remove(modifiedAcc3);
					
					
					newTransaction.setTransactionType(EnumTransaction.CheckBalance);

					currentAccount.addTransaction(newTransaction, transacFee);
					listConsoleTransactions.add(newTransaction);
					
					listCustomerAccounts3.add(modifiedAcc3);
					currentCustomer.setListOfAccounts(listCustomerAccounts3);
					
					System.out.println(currentCustomer + "\n\n" + currentAccount + "\n\n" + newTransaction);

					break;
				case 0:
					System.out.println("Exiting Transaction . . .");
					break;
				}

			} catch (NumberFormatException ex) {
				System.out.println("Invalid Input received. Please try again!");
				tOpt = 3;
				continue;
			}

			
			
		} while (tOpt < 4 && tOpt > 0);


	}



}

