package clientGUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bus.Account;
import bus.CheckingAccount;
import bus.Customer;
import bus.EnumAccount;
import bus.EnumTransaction;
import bus.IAccount;
import bus.ICustomer;
import bus.ITransaction;
import bus.InsufficientFundsException;
import bus.LineOfCreditAccount;
import bus.Transaction;
import data.AccountDB;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;
import javax.swing.JRadioButton;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;

public class ClientGUI extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldAmount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ClientGUI dialog = new ClientGUI(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @throws SQLException 
	 * @throws InsufficientFundsException 
	 * @throws AccountNotFoundException 
	 * @throws NumberFormatException 
	 */
	public ClientGUI(final ICustomer currentCustomer) throws NumberFormatException, InsufficientFundsException, SQLException {

		setTitle("Client System");
		setBounds(100, 100, 450, 348);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblClientMenu = new JLabel("Welcome " + currentCustomer.getfName() + " " + currentCustomer.getlName());
			lblClientMenu.setBounds(29, 11, 397, 34);
			lblClientMenu.setHorizontalAlignment(SwingConstants.CENTER);
			lblClientMenu.setFont(new Font("Tahoma", Font.BOLD, 20));
			contentPanel.add(lblClientMenu);
		}

		final JRadioButton rdbtnChecking = new JRadioButton("Checking");	
		rdbtnChecking.setBounds(31, 85, 111, 23);
		rdbtnChecking.setVisible(false);
		contentPanel.add(rdbtnChecking);

		final JRadioButton rdbtnSavings = new JRadioButton("Savings");
		rdbtnSavings.setBounds(31, 111, 111, 23);
		rdbtnSavings.setVisible(false);
		contentPanel.add(rdbtnSavings);

		final JRadioButton rdbtnLineOfCredit = new JRadioButton("Line of Credit");
		rdbtnLineOfCredit.setBounds(31, 163, 111, 23);
		rdbtnLineOfCredit.setVisible(false);
		contentPanel.add(rdbtnLineOfCredit);

		final JRadioButton rdbtnCurrency = new JRadioButton("Currency");
		rdbtnCurrency.setBounds(31, 137, 111, 23);
		rdbtnCurrency.setVisible(false);
		contentPanel.add(rdbtnCurrency);

		final JRadioButton rdbtnCredit = new JRadioButton("Credit");
		rdbtnCredit.setBounds(31, 189, 111, 23);
		rdbtnCredit.setVisible(false);
		contentPanel.add(rdbtnCredit);

		JLabel lblChooseYourAccountType = new JLabel("Choose your account type:");
		lblChooseYourAccountType.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblChooseYourAccountType.setBounds(10, 61, 167, 23);
		contentPanel.add(lblChooseYourAccountType);

		JLabel lblChooseYourTransaction = new JLabel("Choose your transaction:");
		lblChooseYourTransaction.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblChooseYourTransaction.setBounds(226, 61, 153, 23);
		contentPanel.add(lblChooseYourTransaction);

		final JRadioButton rdbtnWithdraw = new JRadioButton("Withdraw");
		rdbtnWithdraw.setBounds(247, 85, 111, 23);
		contentPanel.add(rdbtnWithdraw);

		final JRadioButton rdbtnDeposit = new JRadioButton("Deposit");
		rdbtnDeposit.setBounds(247, 111, 111, 23);
		contentPanel.add(rdbtnDeposit);

		final JRadioButton rdbtnCheckBalance = new JRadioButton("Check Balance");
		rdbtnCheckBalance.setBounds(247, 137, 111, 23);
		contentPanel.add(rdbtnCheckBalance);

		NumberFormat amountFormat = NumberFormat.getCurrencyInstance();

		final JLabel lblInformAmount = new JLabel("Inform Amount:");
		lblInformAmount.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformAmount.setBounds(226, 178, 153, 14);
		contentPanel.add(lblInformAmount);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSubmit.setBounds(167, 254, 97, 34);
		contentPanel.add(btnSubmit);

		IAccount checkingAccount = currentCustomer.searchAccount(EnumAccount.CheckingAccount);
		IAccount savingsAccount = null;
		IAccount currencyAccount = null;
		IAccount lineOfCreditAccount = null;
		IAccount creditAccount = null;

		savingsAccount = currentCustomer.searchAccount(EnumAccount.SavingsAccount);
		currencyAccount = currentCustomer.searchAccount(EnumAccount.CurrencyAccount);
		lineOfCreditAccount = currentCustomer.searchAccount(EnumAccount.LineOfCreditAccount);
		creditAccount = currentCustomer.searchAccount(EnumAccount.CreditAccount);

		IAccount currentAccount = null;

		if (checkingAccount != null) {
			rdbtnChecking.setVisible(true);
			currentAccount = currentCustomer.searchAccount(EnumAccount.CheckingAccount);
		}
		if (savingsAccount != null) {
			rdbtnSavings.setVisible(true);
			currentAccount = currentCustomer.searchAccount(EnumAccount.SavingsAccount);
		}
		if (currencyAccount != null) {
			rdbtnCurrency.setVisible(true);
			currentAccount = currentCustomer.searchAccount(EnumAccount.CurrencyAccount);
		}
		if (lineOfCreditAccount != null) {
			rdbtnLineOfCredit.setVisible(true);
			currentAccount = currentCustomer.searchAccount(EnumAccount.LineOfCreditAccount);
		}
		if (creditAccount != null) {
			rdbtnCredit.setVisible(true);
			currentAccount = currentCustomer.searchAccount(EnumAccount.CreditAccount);
		}

		rdbtnChecking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnSavings.setSelected(false);
				rdbtnCurrency.setSelected(false);
				rdbtnLineOfCredit.setSelected(false);
				rdbtnCredit.setSelected(false);
			}
		});

		rdbtnSavings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnChecking.setSelected(false);
				rdbtnCurrency.setSelected(false);
				rdbtnLineOfCredit.setSelected(false);
				rdbtnCredit.setSelected(false);
			}
		});

		rdbtnCurrency.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnChecking.setSelected(false);
				rdbtnSavings.setSelected(false);
				rdbtnLineOfCredit.setSelected(false);
				rdbtnCredit.setSelected(false);
			}
		});

		rdbtnLineOfCredit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnChecking.setSelected(false);
				rdbtnSavings.setSelected(false);
				rdbtnCurrency.setSelected(false);
				rdbtnCredit.setSelected(false);
			}
		});

		rdbtnCredit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnChecking.setSelected(false);
				rdbtnSavings.setSelected(false);
				rdbtnCurrency.setSelected(false);
				rdbtnLineOfCredit.setSelected(false);
			}
		});

		rdbtnWithdraw.setSelected(true);

		textFieldAmount = new JTextField();
		textFieldAmount.setBounds(236, 211, 143, 20);
		contentPanel.add(textFieldAmount);
		textFieldAmount.setColumns(10);

		rdbtnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnDeposit.setSelected(false);
				rdbtnCheckBalance.setSelected(false);

				lblInformAmount.setVisible(true);
				//				formattedTextFieldAmount.setVisible(true);
			}
		});

		rdbtnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnWithdraw.setSelected(false);
				rdbtnCheckBalance.setSelected(false);

				lblInformAmount.setVisible(true);
				//				formattedTextFieldAmount.setVisible(true);
			}
		});

		rdbtnCheckBalance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnDeposit.setSelected(false);
				rdbtnWithdraw.setSelected(false);

				lblInformAmount.setVisible(false);
				//				formattedTextFieldAmount.setVisible(false);
			}
		});

		//final ITransaction newTransaction = new Transaction();
		//newTransaction.setAccountNumber(currentAccount.getAccountNumber());
		//final IAccount currentAccount2 = currentAccount;
		//ArrayList<ITransaction> listConsoleTransactions = new ArrayList<ITransaction>();
		//int cpt = 0;
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double transacFee = 0;
				ITransaction newTransaction = new Transaction();

				IAccount modAccount = null;
				if (rdbtnChecking.isSelected()) {
					try {
						modAccount = AccountDB.search(currentCustomer, EnumAccount.CheckingAccount);
						
						int cpt = modAccount.getListOfTransactions().size();
						if (cpt > ((CheckingAccount) modAccount).getFreeTransactions()) {
							transacFee = ((CheckingAccount) modAccount).getTransactionFee();
						}
						
					} catch (NumberFormatException | SQLException | InsufficientFundsException e1) {
						e1.printStackTrace();
					}
				} else if (rdbtnSavings.isSelected()) {
					try {
						modAccount = AccountDB.search(currentCustomer, EnumAccount.SavingsAccount);
					} catch (NumberFormatException | SQLException | InsufficientFundsException e1) {
						e1.printStackTrace();
					}
				} else if (rdbtnCurrency.isSelected()) {
					try {
						modAccount = AccountDB.search(currentCustomer, EnumAccount.CurrencyAccount);
					} catch (NumberFormatException | SQLException | InsufficientFundsException e1) {
						e1.printStackTrace();
					}
				} else if (rdbtnLineOfCredit.isSelected()) {
					try {
						modAccount = AccountDB.search(currentCustomer, EnumAccount.LineOfCreditAccount);
					} catch (NumberFormatException | SQLException | InsufficientFundsException e1) {
						e1.printStackTrace();
					}
				} else if (rdbtnCredit.isSelected()) {
					try {
						modAccount = AccountDB.search(currentCustomer, EnumAccount.CreditAccount);
					} catch (NumberFormatException | SQLException | InsufficientFundsException e1) {
						e1.printStackTrace();
					}
				}

				//newTransaction.setAccountNumber(currentAccount2.getAccountNumber());
				newTransaction.setAccountNumber(modAccount.getAccountNumber());
				
				boolean goodAmount = true;				
				/*
				if (modAccount.getType().equals(EnumAccount.CheckingAccount)) {
					
					int cpt = modAccount.getListOfTransactions().size();

					if (cpt > ((CheckingAccount) modAccount).getFreeTransactions()) {
						transacFee = ((CheckingAccount) modAccount).getTransactionFee();
					}

					for (ITransaction x : modAccount.getListOfTransactions()) {
						JOptionPane.showMessageDialog(null, x);
					}
					//JOptionPane.showMessageDialog(null, cpt);
				}
				*/
				if (!Pattern.matches("^\\d+(\\.\\d{1,2})?$", textFieldAmount.getText()) && !rdbtnCheckBalance.isSelected()) {
					JOptionPane.showMessageDialog(null, "Enter Valid Amount [no trailing decimal points]");
					//textFieldAmount.setText("");
					goodAmount = false;
				}

				Double amount = 0.0;

				if (!textFieldAmount.getText().isEmpty()) {
					try {
						amount = Double.valueOf(textFieldAmount.getText());
					} catch (NumberFormatException ex) {
					}
				}

				//IAccount modAccount = null;
				try {
					modAccount = AccountDB.search(currentCustomer,newTransaction.getAccountNumber());
				} catch (NumberFormatException | SQLException | InsufficientFundsException e1) {
					e1.printStackTrace();
				}





				if (textFieldAmount.getText().isEmpty() && !rdbtnCheckBalance.isSelected()) {
					JOptionPane.showMessageDialog(null, "Amount field is empty");
				} else if (!rdbtnWithdraw.isSelected() && !rdbtnDeposit.isSelected() && !rdbtnCheckBalance.isSelected()) {
					JOptionPane.showMessageDialog(null, "Please select any option");
				} else if (!goodAmount && !rdbtnCheckBalance.isSelected()) {
					JOptionPane.showMessageDialog(null, "Wrong format.\nPlease input the format"
							+ "as the examples below:\n"
							+ "999.99 or 999.");
				} else if (rdbtnWithdraw.isSelected()) {
					newTransaction.setTransactionType(EnumTransaction.Withdraw);
					if (rdbtnChecking.isSelected()) {
						newTransaction.setAmount(amount);
						try {
							//currentCustomer.addTransaction(newTransaction, transacFee);
							modAccount.addTransaction(newTransaction, transacFee);
							JOptionPane.showMessageDialog(null, "\n\n" + modAccount + "\n\n" + newTransaction );
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InsufficientFundsException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					}

					
				} else if (rdbtnDeposit.isSelected()) {

					newTransaction.setTransactionType(EnumTransaction.Deposit);
					newTransaction.setAmount(amount);


					if (modAccount.getType() == EnumAccount.LineOfCreditAccount || modAccount.getType() == EnumAccount.CreditAccount) {
						double bal = ((LineOfCreditAccount) modAccount).getCurrentBalance();
						if (bal < amount) {
							((LineOfCreditAccount) modAccount).setCurrentBalance(0);
						} else {
							((LineOfCreditAccount) modAccount).setCurrentBalance(bal - amount);
						}
					}

					try {
						//currentCustomer.addTransaction(newTransaction, transacFee);
						modAccount.addTransaction(newTransaction, transacFee);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InsufficientFundsException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					JOptionPane.showMessageDialog(null, "\n\n" + modAccount + "\n\n" + newTransaction );
				} else if (rdbtnCheckBalance.isSelected()) {

					newTransaction.setTransactionType(EnumTransaction.CheckBalance);
					newTransaction.setAmount(0);

					try {
						modAccount.addTransaction(newTransaction, transacFee);
					} catch (SQLException | InsufficientFundsException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					JOptionPane.showMessageDialog(null, "\n\n" + modAccount + "\n\n" + newTransaction );
				}


			}
		});
	}
}
