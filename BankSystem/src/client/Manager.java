package client;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import bus.AccountsList;
import bus.BankManager;
import bus.CreditAccount;
import bus.CurrencyAccount;
import bus.Customer;
import bus.CustomersList;
import bus.Date;
import bus.EnumCurrency;
import bus.IAccount;
import bus.ICustomer;
import bus.ITransaction;
import bus.InsufficientFundsException;
import bus.LineOfCreditAccount;
import bus.ManagersList;
import bus.NotInRangeException;
import bus.PredicateAlgorithm;
import bus.PredicateCustomerId;
import bus.PredicateFirstName;
import bus.PredicateLastName;
import bus.SavingsAccount;
import bus.User;

public class Manager {

	public static void main(String[] args) throws InsufficientFundsException, ClassNotFoundException, SQLException, IOException {

		Scanner in = new Scanner(System.in);
		Integer menu = 0;
		do {
			
			System.out.println("[1] Register");
			System.out.println("[2] Log In");
			System.out.println("[0] Log Out");
			System.out.print("Enter your choice: ");
			menu  = Integer.valueOf(in.next());
			
			while (menu < 0 || menu > 2) {
				System.out.print(" Enter valid option: ");
				menu = Integer.valueOf(in.next());
			}
			
			switch(menu) {
			case 1:
				System.out.println("\n+++++++++ REGISTER A MANAGER +++++++++\n");
				System.out.print("Enter Manager ID: ");
				String managerId = in.next();

				while (!Pattern.matches("^M\\d\\d\\d\\d$", managerId)) {
					System.out.print("Enter Valid Customer ID [use 'CXXXX' where X is a number from [0-9]] : ");
					managerId = in.next();
				}

				System.out.print("Enter Manager PIN: ");
				String managerPin = in.next();
				System.out.print("Enter Manager First Name: ");
				String managerFirstName = in.next();
				System.out.print("Enter Manager Last Name: ");
				String managerLastName = in.next();

				System.out.print("Enter Manager Email: ");
				String managerEmail = in.next();

				while (!Pattern.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", managerEmail)) {
					System.out.print("Enter Valid Customer Email [user@mail.com] : ");
					managerEmail = in.next();
				}

				System.out.print("Enter Manager Phone: ");
				String managerPhone = in.next();

				while (!Pattern.matches("^(\\(\\d{3}\\)|\\d{3})[-\\s]?\\d{3}[-\\s]?\\d{4}$", managerPhone)) {
					System.out.print("Enter Valid Customer Phone [(123) 456-7890 | 123-456-7890 |1234567890] : ");
					managerPhone = in.next();
				}

				BankManager newManager = new BankManager(managerId, managerPin, managerFirstName, managerLastName, managerEmail, managerPhone);
				ManagersList.add(newManager);
				System.out.println("\n" + newManager);
				System.out.println("\nManager successfully added!\n");
				ManagersList.writeToFile();
				break;
			case 2:
				ManagersList.setListManagers(ManagersList.readFromFile());
				System.out.println("\n+++++++++ SIGN IN AS A MANAGER +++++++++\n");
				ManagerUI();
				break;
			case 0:
				System.out.println("\n\tLogging out . . .\n");
				break;
			default:
				break;
			}
			
			
		} while (menu < 3 && menu > 0);
		
		in.close();

	}

	public static void ManagerUI() throws ClassNotFoundException, IOException, InsufficientFundsException, SQLException {

		Scanner in = new Scanner(System.in);
		boolean init = false;
		
		System.out.print("Enter your ID: ");
		String id = in.next();
		System.out.print("Enter your PIN: ");
		String pin = in.next();
		
		BankManager manager = ManagersList.search(id, pin);

		ArrayList<ICustomer> listConsoleCustomers = null;
		
		if (manager == null) {
			System.out.println("\nManager does not exist!\n");
		} else if (manager.getUserId().equals(id) && manager.getPin().equals(pin)) {

			int option = 0;
			do {
				try {

					if (init == true) {
						CustomersList.writeToFile();
					}
					
					try {
						listConsoleCustomers = CustomersList.readFromFile();
					} catch (Exception ex) {
						System.out.println("No Customers In File Found.\nAdd A New Customer To Proceed!");
						option = 11;
					}

					ArrayList<IAccount> listFileAccounts = new ArrayList<IAccount>();
					
					if (listConsoleCustomers != null) {
						CustomersList.setListCustomers(listConsoleCustomers);
						
						for (ICustomer cust : listConsoleCustomers) {
							listFileAccounts.addAll(cust.getListOfAccounts());
						}
						
						AccountsList.setListAccounts(listFileAccounts);
					} 

					System.out.println("\n+++++++++ WELCOME BANK MANAGER +++++++++");
					System.out.println("[1] Create Customer");
					System.out.println("[2] Remove Customer");
					System.out.println("[3] Add Account");
					System.out.println("[4] Remove Account");
					System.out.println("[5] Display Customers From Console");
					System.out.println("[6] Read Customers From File");
					System.out.println("[7] Save Customers To File");
					System.out.println("[8] Sort Customers");
					System.out.println("[9] View Customer Accounts");
					System.out.println("[10] View All Accounts");
					System.out.println("[11] Modify Customer");
					System.out.println("[0] Log Out");
					System.out.print("Enter Choice: ");
					option = Integer.valueOf(in.next());

					while (option < 0 || option > 11) {
						System.out.print(" Enter valid choice: ");
						option = Integer.valueOf(in.next());
					}

					switch (option) {
					case 1:
						System.out.print("Enter Customer ID: ");
						String customerId = in.next();

						while (!Pattern.matches("^C\\d\\d\\d\\d$", customerId)) {
							System.out.print("Enter Valid Customer ID [use 'CXXXX' where X is a number from [0-9]] : ");
							customerId = in.next();
						}

						System.out.print("Enter Customer PIN: ");
						String customerPin = in.next();
						System.out.print("Enter Customer First Name: ");
						String customerFirstName = in.next();
						System.out.print("Enter Customer Last Name: ");
						String customerLastName = in.next();

						System.out.print("Enter Customer Email: ");
						String customerEmail = in.next();

						while (!Pattern.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", customerEmail)) {
							System.out.print("Enter Valid Customer Email [user@mail.com] : ");
							customerEmail = in.next();
						}

						System.out.print("Enter Customer Phone: ");
						String customerPhone = in.next();

						while (!Pattern.matches("^(\\(\\d{3}\\)|\\d{3})[-\\s]?\\d{3}[-\\s]?\\d{4}$", customerPhone)) {
							System.out.print("Enter Valid Customer Phone [(123) 456-7890 | 123-456-7890 |1234567890] : ");
							customerPhone = in.next();
						}

						ICustomer newCustomer = new Customer(customerId, customerPin, customerFirstName, customerLastName, customerEmail, customerPhone);
						manager.createCustomer(newCustomer);
						System.out.println("\n" + newCustomer);
						System.out.println("\nCustomer successfully added!\n");

						break;

					case 2:
						System.out.print("Enter Customer ID to remove: ");
						String customerIdToRemove = in.next();

						ICustomer customerToRemove = CustomersList.search(customerIdToRemove);
						if (customerToRemove != null) {
							manager.removeCustomer(customerToRemove);
							System.out.println("\nCustomer successfully removed!\n");
						} else {
							System.out.println("Customer not found.");
						}
						break;

					case 3:
						System.out.print("Enter Customer ID: ");
						String customerId1 = in.next();

						ICustomer customerToAddAccount = CustomersList.search(customerId1);

						if (customerToAddAccount != null) {
							Integer type = 0;

							System.out.print("Enter Account Number: ");
							String accountNumber = in.next();

							while (!Pattern.matches("^A\\d\\d\\d\\d$", accountNumber)) {
								System.out.print("Enter Valid Account Number [use 'AXXXX' where X is a number from [0-9]] : ");
								accountNumber = in.next();
							}


							try {
								System.out.println("[1] Savings");
								System.out.println("[2] Currency");
								System.out.println("[3] Line Of Credit");
								System.out.println("[4] Credit");
								System.out.println("[0] Go Back");

								System.out.print("Enter Account Type: ");
								type = Integer.valueOf(in.next());

								while (type < 0 || type > 4) {
									System.out.print(" Enter valid account type: ");
									type = Integer.valueOf(in.next());
								}

								System.out.print("Enter Date (dd-mm-yyyy): ");
								String openedDateStr = in.next();
								Date openedDate = new Date(openedDateStr);

								IAccount newAccount = null;

								switch(type) {
								case 1:
									newAccount = new SavingsAccount(accountNumber, openedDate, 0,0,0,0);
									break;
								case 2:
									Integer currency = 0;
									System.out.println("[1] USD");
									System.out.println("[2] JPY");
									System.out.println("[3] EUR");
									System.out.println("[0] Go Back");

									System.out.print("Enter Currency: ");
									currency = Integer.valueOf(in.next());

									while (currency < 0 || currency > 3) {
										System.out.print(" Enter valid choice: ");
										currency = Integer.valueOf(in.next());
									}

									EnumCurrency currencyType = EnumCurrency.Undefined;

									switch (currency) {
									case 1:
										currencyType = EnumCurrency.USD;
										break;
									case 2:
										currencyType = EnumCurrency.JPY;
										break;
									case 3:
										currencyType = EnumCurrency.EUR;
										break;
									case 0:
										System.out.println("\tGoing back . . .");
										break;
									default:
										break;
									}

									System.out.print("Enter Exchange Rate (in CAD): ");
									double exrate = in.nextDouble();

									newAccount = new CurrencyAccount(accountNumber,openedDate,0,0,currencyType,exrate);
									break;
								case 3:
									newAccount = new LineOfCreditAccount(accountNumber, openedDate, 0,0,0,0);
									break;
								case 4:
									newAccount = new CreditAccount(accountNumber, openedDate, 0,0,0,0,0);
									break;
								case 0:
									System.out.println("\tGoing back . . .");
									break;
								} 
								
								/*
								// commented out since return type changed for GUI
								boolean success = manager.addAccount(customerToAddAccount, newAccount);

								if (success) {
									System.out.println("\nCongratulations! Account added successfully.");
								} else {
									System.out.println("\nCheck with Bank Manager. Account already existing.");
								}
								*/
								
							} catch (NumberFormatException ex) {
								System.out.println("Invalid Input received. Please try again!");
							}


						} else {
							System.out.println("Customer not found.");
						}

						break;
					case 4:
						System.out.print("Enter Customer ID: ");
						String customerId2 = in.next();

						ICustomer customerToDelAccount = CustomersList.search(customerId2);

						if (customerToDelAccount != null) {
							System.out.print("Enter Account Number: ");
							String accountNumber = in.next();
							
							/*
							// commented out since return type changed for GUI
							if (!manager.removeAccount(customerToDelAccount, accountNumber)) {
								System.out.println("\nAccount not found!");
							} else {
								System.out.println("\nAccount removed successfully.");
							}
							 */
							
						} else {
							System.out.println("Customer not found.");
						}
						
						break;
					case 5:
						System.out.println("\n <========== CUSTOMERS FROM CONSOLE ==========>\n");

						if (CustomersList.getListCustomers().size() > 0) {
							for (ICustomer cust : CustomersList.getListCustomers()) {
								System.out.println(cust);
							}
						} else {
							System.out.println("\tNo Customers From Console Found");
						}

						break;
					case 6:
						for (ICustomer cust : CustomersList.readFromFile()) {
							System.out.println(cust);
						}
						break;
					case 7:
						CustomersList.writeToFile();
						System.out.println("Customers saved to file!\n");
						break;
					case 8:
						int sortOpt = 0;
						do {
							try {
								System.out.println("[1] Sort By Id");
								System.out.println("[2] Sort By First Name");
								System.out.println("[3] Sort By Last Name");
								System.out.println("[0] Exit");
								System.out.print("Enter Choice: ");
								sortOpt = Integer.valueOf(in.next());

								while (sortOpt < 0 || sortOpt > 3) {
									System.out.print(" Enter valid account type: ");
									sortOpt = Integer.valueOf(in.next());
								}

								ArrayList<ICustomer> listSortCustomers = CustomersList.getListCustomers();

								switch (sortOpt) {
								case 1: 
									System.out.println("\n <========== SORTED LIST - CUSTOMER ID ==========>\n");
									PredicateAlgorithm.sort(listSortCustomers, new PredicateCustomerId());

									for(ICustomer element : listSortCustomers) {
										System.out.println(element);
									}
									
									break;
								case 2: 
									System.out.println("\n <========== SORTED LIST - FIRST NAME ==========>\n");
									PredicateAlgorithm.sort(listSortCustomers, new PredicateFirstName());

									for(ICustomer element : listSortCustomers) {
										System.out.println(element);
									}
									break;
								case 3: 
									System.out.println("\n <========== SORTED LIST - LAST NAME ==========>\n");
									PredicateAlgorithm.sort(listSortCustomers, new PredicateLastName());

									for(ICustomer element : listSortCustomers) {
										System.out.println(element);
									}
									break;
								case 0: 
									System.out.println("\tExiting ...");
									break;
								default: 
									break;
								}
							} catch (NumberFormatException ex) {
								System.out.println("Invalid Input received. Please try again!");
								sortOpt = 3;
								continue;
							}

						} while (sortOpt < 4 && sortOpt > 0);

						break;
					case 9:
						System.out.print("Enter Customer ID: ");

						ICustomer customerToViewAccs = CustomersList.search(in.next());

						if (customerToViewAccs != null) {

							System.out.println("\n" + customerToViewAccs.getfName() + "'s List Of Accounts\n-----------------------------");

							for(IAccount element : manager.getAllCustomerAccounts(customerToViewAccs)) {
								System.out.println("\n" + element);
							}
						} else {
							System.out.println("Customer not found.");
						}
						break;
					case 10:	
						int displayOpt = 0;
						do {
							try {
								System.out.println("\nChoose Accounts to display:");
								System.out.println("[1] Checking");
								System.out.println("[2] Savings");
								System.out.println("[3] Currency");
								System.out.println("[4] Line Of Credit");
								System.out.println("[5] Credit");
								System.out.println("[0] Go Back");
								System.out.print("Enter Choice: ");
								//displayOpt = in.nextInt();	
								displayOpt = Integer.valueOf(in.next());

								while (displayOpt < 0 || displayOpt > 5) {
									System.out.print(" Enter valid account type: ");
									displayOpt = Integer.valueOf(in.next());
								}

								switch (displayOpt) {
								case 1: 
									System.out.println("\n <========== CHECKING ACCOUNTS ==========>\n");

									if (AccountsList.getCheckingAccounts().size() > 0) {
										for(IAccount acc : AccountsList.getCheckingAccounts()) {
											System.out.println("\n" + acc);
										}
									} else {
										System.out.println("No Accounts Found");
									}

									break;
								case 2: 
									System.out.println("\n <========== SAVINGS ACCOUNTS ==========>\n");
									if (AccountsList.getSavingsAccounts().size() > 0) {
										for(IAccount acc : AccountsList.getSavingsAccounts()) {
											System.out.println("\n" + acc);
										}
									} else {
										System.out.println("No Accounts Found");
									}
									break;
								case 3: 
									System.out.println("\n <========== CURRENCY ACCOUNTS ==========>\n");
									if (AccountsList.getCurrencyAccounts().size() > 0) {
										for(IAccount acc : AccountsList.getCurrencyAccounts()) {
											System.out.println("\n" + acc);
										}
									} else {
										System.out.println("No Accounts Found");
									}
									break;
								case 4: 
									System.out.println("\n <========== LINE OF CREDIT ACCOUNTS ==========>\n");
									if (AccountsList.getLineOfCreditAccounts().size() > 0) {
										for(IAccount acc : AccountsList.getLineOfCreditAccounts()) {
											System.out.println("\n" + acc);
										}
									} else {
										System.out.println("No Accounts Found");
									}
									break;
								case 5: 
									System.out.println("\n <========== CREDIT ACCOUNTS ==========>\n");
									if (AccountsList.getCreditAccounts().size() > 0) {
										for(IAccount acc : AccountsList.getCreditAccounts()) {
											System.out.println("\n" + acc);
										}
									} else {
										System.out.println("No Accounts Found");
									}
									break;
								default: 
									break;
								}	
							} catch (NumberFormatException ex) {
								System.out.println("Invalid Input received. Please try again!");
								displayOpt = 5;
								continue;
							}


						} while (displayOpt < 6 && displayOpt > 0);

						break;
					case 11:
						try {
							System.out.println("\n <========== MODIFYING A CUSTOMER ==========>\n");
							System.out.print("Enter your CustomerID: ");
							String cId = in.next();
							System.out.print("Enter your old PIN: ");
							String cPin = in.next();
							
							ICustomer modifiedCustomer = manager.searchCustomer(cId);
							ICustomer updatedCustomer = modifiedCustomer;
							
							ArrayList<ICustomer> listCustomers = CustomersList.getListCustomers();
							listCustomers.remove(modifiedCustomer);
							
							System.out.print("Enter your new PIN: ");
							String cNewPin1 = in.next();
							
							System.out.print("Enter your new PIN again: ");
							String cNewPin2 = in.next();
							
							if (cNewPin1.equals(cNewPin2)) {
								((User) updatedCustomer).setPin(cNewPin2);
								System.out.print("\nCustomer updated successfully!\n");
							}
							
							listCustomers.add(updatedCustomer);
							CustomersList.setListCustomers(listCustomers);
						} catch (NotInRangeException ex) {
							System.out.println("Invalid PIN received. Please try again with the format XXXX where X is a digit from 0 - 9!");
							option = 11;
							continue;
						}
						
						break;
					case 0:
						System.out.println("\tLogging out...");
						
						break;
					default:
						System.out.println("Invalid choice. Please try again.");
						break;
					}

				} catch (NumberFormatException ex) {
					System.out.println("Invalid Input received. Please try again!");
					option = 11;
					continue;
				} 
				
				init = true;
			} while (option < 12 && option > 0);
		}
		else {
			System.out.println("Invalid credentials");
		}
	}

}

