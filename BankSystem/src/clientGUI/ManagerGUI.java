 package clientGUI;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import bus.*;
import data.*;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JComboBox;

public class ManagerGUI extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldCustomerId;
	private JTextField textFieldCustomerPin;
	private JTextField textFieldCustomerFirstName;
	private JTextField textFieldCustomerLastName;
	private JTable table;
	private JTextField textFieldCustomerId_1;
	private JTextField textFieldAccountNumber;
	private JTextField textFieldEmail;
	private JTextField textFieldPhone;
	private JTextField textFieldCustomerId_2;
	private JTextField textFieldCustomerIdCustomerAccounts;
	private JTextField textFieldOldPin;
	private JTextField textFieldCustomerNewPin;
	private JTextField textFieldConfirmCustomerNewPin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ManagerGUI dialog = new ManagerGUI(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @throws InsufficientFundsException 
	 * @throws SQLException 
	 * @throws NumberFormatException 
	 */
	@SuppressWarnings("unchecked")
	public ManagerGUI(final BankManager bm1) throws NumberFormatException, SQLException, InsufficientFundsException {
		
		final MainBankManager main = new MainBankManager(); // I am using this main object to use the objects from class Main()
		final int counter = 0; 
		final ArrayList<ICustomer> listCustomer = new ArrayList<ICustomer>();
		final ICustomer newCustomer;
		final Random random = new Random();
		
		// This block sets the configuration of the window.
		setTitle("Manager Menu");
		setBounds(100, 100, 736, 466);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Welcome " + bm1.getfName() + " " + bm1.getlName());
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblNewLabel.setBounds(10, 11, 416, 29);
			contentPanel.add(lblNewLabel);
		}
		
		// Inside the window we have a tabbed pane, which is declared here
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 56, 702, 329);

		contentPanel.add(tabbedPane);
		
		JPanel panelViewCustomerAccounts = new JPanel();
		tabbedPane.addTab("View Customer Accounts", null, panelViewCustomerAccounts, null);
		panelViewCustomerAccounts.setLayout(null);
		
		JLabel lblViewCustomerAccounts = new JLabel("View Customer Accounts");
		lblViewCustomerAccounts.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewCustomerAccounts.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblViewCustomerAccounts.setBounds(10, 53, 677, 30);
		panelViewCustomerAccounts.add(lblViewCustomerAccounts);
		
		JLabel lblCustomerIdViewCustomerAccounts = new JLabel("Customer ID:");
		lblCustomerIdViewCustomerAccounts.setHorizontalAlignment(SwingConstants.CENTER);
		lblCustomerIdViewCustomerAccounts.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblCustomerIdViewCustomerAccounts.setBounds(241, 94, 118, 30);
		panelViewCustomerAccounts.add(lblCustomerIdViewCustomerAccounts);
		
		textFieldCustomerIdCustomerAccounts = new JTextField();
		textFieldCustomerIdCustomerAccounts.setColumns(10);
		textFieldCustomerIdCustomerAccounts.setBounds(358, 100, 96, 20);
		panelViewCustomerAccounts.add(textFieldCustomerIdCustomerAccounts);
		
		JButton btnViewCustommerAccounts = new JButton("Submit");
		btnViewCustommerAccounts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textFieldCustomerFirstName.setText("");
				
				String customerIdToShow = textFieldCustomerIdCustomerAccounts.getText();
				
				Pattern idPattern = Pattern.compile("^C\\d\\d\\d\\d$");
				Matcher matchId = idPattern.matcher(customerIdToShow);
				
				boolean matchFoundId = matchId.find();
				
				if (customerIdToShow.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Some field is empty.");
				} else if (!matchFoundId) {
					JOptionPane.showMessageDialog(null, "Customer ID is in wrong format\n"
							+ "Must be 'C' followed by four digits\nExample: C9999");
				} else {
					ICustomer oneCustomer = null;
					try {
						oneCustomer = CustomerDB.search(customerIdToShow);
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InsufficientFundsException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ArrayList<IAccount> listAccountsFromDb = null;
					try {
						listAccountsFromDb = AccountDB.selectAll(oneCustomer);
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InsufficientFundsException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					if (listAccountsFromDb != null) {
						
						String newLabel = listAccountsFromDb.toString();
						JTextArea textAreaNewLabel = new JTextArea(20,30);
						textAreaNewLabel.append(newLabel);
						JScrollPane scrollPaneNewLabel = new JScrollPane(textAreaNewLabel);
						scrollPaneNewLabel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
						JOptionPane.showMessageDialog(null, scrollPaneNewLabel);
						
					} else {
						JOptionPane.showMessageDialog(null, "Customer not found.");
					}
				}			
			}
		});
		btnViewCustommerAccounts.setBounds(300, 153, 89, 23);
		panelViewCustomerAccounts.add(btnViewCustommerAccounts);
		
		JPanel panelViewAllAccounts = new JPanel();
		tabbedPane.addTab("View All Accounts", null, panelViewAllAccounts, null);
		panelViewAllAccounts.setLayout(null);
		
		JButton btnShowAllAccounts = new JButton("Show All Accounts");
		btnShowAllAccounts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					ArrayList<IAccount> account = AccountDB.selectAllAccounts();
					String newLabel = account.toString();
					JTextArea textAreaNewLabel = new JTextArea(20,30);
					textAreaNewLabel.append(newLabel);
					JScrollPane scrollPaneNewLabel = new JScrollPane(textAreaNewLabel);
					scrollPaneNewLabel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
					JOptionPane.showMessageDialog(null, scrollPaneNewLabel);						
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InsufficientFundsException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
		});
		btnShowAllAccounts.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnShowAllAccounts.setBounds(223, 152, 250, 45);
		panelViewAllAccounts.add(btnShowAllAccounts);
		
		JLabel lblNewLabel_1 = new JLabel("Show All Accounts");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(10, 41, 677, 33);
		panelViewAllAccounts.add(lblNewLabel_1);
		{
			// This block refers to the ADD / REMOVE CUSTOMER action
			JPanel panelAddRemoveCustomer = new JPanel();
			tabbedPane.addTab("Add / Remove Customer", null, panelAddRemoveCustomer, null);
			panelAddRemoveCustomer.setLayout(null);
			{
				JLabel lblCustomerId = new JLabel("Customer ID:");
				lblCustomerId.setHorizontalAlignment(SwingConstants.RIGHT);
				lblCustomerId.setFont(new Font("Tahoma", Font.PLAIN, 16));
				lblCustomerId.setBounds(85, 16, 104, 28);
				panelAddRemoveCustomer.add(lblCustomerId);
			}
			{
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(231, 11, 2, 2);
				panelAddRemoveCustomer.add(scrollPane);
			}
			{
				JLabel lblCustomerPin = new JLabel("Customer PIN:");
				lblCustomerPin.setHorizontalAlignment(SwingConstants.RIGHT);
				lblCustomerPin.setFont(new Font("Tahoma", Font.PLAIN, 16));
				lblCustomerPin.setBounds(66, 60, 123, 28);
				panelAddRemoveCustomer.add(lblCustomerPin);
			}
			{
				JLabel lblCustomerFirstName = new JLabel("Customer First Name:");
				lblCustomerFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
				lblCustomerFirstName.setFont(new Font("Tahoma", Font.PLAIN, 16));
				lblCustomerFirstName.setBounds(34, 104, 155, 28);
				panelAddRemoveCustomer.add(lblCustomerFirstName);
			}
			{
				JLabel lblCustomerLastName = new JLabel("Customer Last Name:");
				lblCustomerLastName.setHorizontalAlignment(SwingConstants.RIGHT);
				lblCustomerLastName.setFont(new Font("Tahoma", Font.PLAIN, 16));
				lblCustomerLastName.setBounds(21, 148, 168, 28);
				panelAddRemoveCustomer.add(lblCustomerLastName);
			}
			{
				textFieldCustomerId = new JTextField();
				textFieldCustomerId.setText("C");
				textFieldCustomerId.setBounds(199, 22, 188, 20);
				panelAddRemoveCustomer.add(textFieldCustomerId);
				textFieldCustomerId.setColumns(10);
			}
			{
				textFieldCustomerPin = new JTextField();
				textFieldCustomerPin.setColumns(10);
				textFieldCustomerPin.setBounds(199, 66, 188, 20);
				panelAddRemoveCustomer.add(textFieldCustomerPin);
			}
			{
				textFieldCustomerFirstName = new JTextField();
				textFieldCustomerFirstName.setColumns(10);
				textFieldCustomerFirstName.setBounds(199, 110, 188, 20);
				panelAddRemoveCustomer.add(textFieldCustomerFirstName);
			}
			{
				textFieldCustomerLastName = new JTextField();
				textFieldCustomerLastName.setColumns(10);
				textFieldCustomerLastName.setBounds(199, 154, 188, 20);
				panelAddRemoveCustomer.add(textFieldCustomerLastName);
			}
			final JRadioButton rdbtnAddCustomer = new JRadioButton("Add Customer");			
			rdbtnAddCustomer.setBounds(494, 16, 111, 23);
			panelAddRemoveCustomer.add(rdbtnAddCustomer);
			
			rdbtnAddCustomer.setSelected(true);
			textFieldCustomerId.setEditable(false);

			final JRadioButton rdbtnRemoveCustomer = new JRadioButton("Remove Customer");
			rdbtnRemoveCustomer.setBounds(494, 42, 144, 23);
			panelAddRemoveCustomer.add(rdbtnRemoveCustomer);
			
			final JButton btnAddCustomer = new JButton("Add Customer");
			final JButton btnRemoveCustomer = new JButton("Remove Customer");
			
			btnAddCustomer.setVisible(true);
			btnRemoveCustomer.setVisible(false);

			rdbtnAddCustomer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rdbtnRemoveCustomer.setSelected(false);
					textFieldCustomerId.setEditable(false);
					textFieldCustomerPin.setEditable(true);
					textFieldCustomerFirstName.setEditable(true);
					textFieldCustomerLastName.setEditable(true);
					textFieldEmail.setEditable(true);
					textFieldPhone.setEditable(true);
					btnAddCustomer.setVisible(true);
					btnRemoveCustomer.setVisible(false);

				}
			});
			
			rdbtnRemoveCustomer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rdbtnAddCustomer.setSelected(false);
					textFieldCustomerId.setEditable(true);
					textFieldCustomerPin.setEditable(false);
					textFieldCustomerFirstName.setEditable(false);
					textFieldCustomerLastName.setEditable(false);
					textFieldEmail.setEditable(false);
					textFieldPhone.setEditable(false);
					btnAddCustomer.setVisible(false);
					btnRemoveCustomer.setVisible(true);
				}
			});
			{
				
				// This action listener is used to create the customer (ADD CUSTOMER BUTTON)
				btnAddCustomer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int id = random.nextInt((9999-1000) + 1) + 1000;
						boolean goodEmail = true;
						boolean goodPhone = true;
						
						String customerId = "C"+Integer.toString(id);
						//textFieldCustomerId.setText(customerId);
						String firstName = textFieldCustomerFirstName.getText();
						String lastName = textFieldCustomerLastName.getText();
						String pin = textFieldCustomerPin.getText();
						
						String email = textFieldEmail.getText();
						
						if (!Pattern.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", email)) {
							JOptionPane.showMessageDialog(null, "Enter Valid Customer Email [user@mail.com]");
							textFieldEmail.setText("");
							goodEmail = false;
						}
						
						String phone = textFieldPhone.getText();
						
						if (!Pattern.matches("^(\\(\\d{3}\\)|\\d{3})[-\\s]?\\d{3}[-\\s]?\\d{4}$", phone)) {
							JOptionPane.showMessageDialog(null, "Enter Valid Customer Phone [(123) 456-7890 | 123-456-7890 | 1234567890]");
							textFieldPhone.setText("");
							goodPhone = false;
						}
						
						Pattern namePattern = Pattern.compile("^[a-zA-Z\\s-]+$");
						Matcher matchFirstName = namePattern.matcher(firstName);
						Matcher matchLastName = namePattern.matcher(lastName);
						
						boolean matchFoundFirstName = matchFirstName.find();
						boolean matchFoundLastName = matchLastName.find();
						
						if (firstName.isEmpty() || lastName.isEmpty() || pin.isEmpty() || email.isEmpty() || phone.isEmpty()) {
							JOptionPane.showMessageDialog(null, "Some field is empty");
						} else if (!matchFoundLastName) {
							JOptionPane.showMessageDialog(null, "Last Name\nInvalid character on " + lastName);
						} else if (!matchFoundFirstName) {
							JOptionPane.showMessageDialog(null, "First Name\nInvalid character on " + firstName);
						} else if (!goodEmail) {
							JOptionPane.showMessageDialog(null, "Fill Email Information");
						} else if (!goodPhone) {
							JOptionPane.showMessageDialog(null, "Fill Phone Information");
						} else {
							ICustomer newCustomer = new Customer(customerId, pin, firstName, lastName,email,phone);
							try {
								bm1.createCustomer(newCustomer);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							JOptionPane.showMessageDialog(null, "Customer added: \nFirst Name: " + firstName
									+ "\nLast Name: " + lastName + "\nCustomer ID: " + customerId);
						}											
					}
				});
				btnAddCustomer.setBounds(494, 89, 124, 28);
				panelAddRemoveCustomer.add(btnAddCustomer);
			}
			{
				// This action listener is used to remove the customer (REMOVE CUSTOMER BUTTON)
				btnRemoveCustomer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						textFieldCustomerFirstName.setText("");
						textFieldCustomerLastName.setText("");
						textFieldCustomerPin.setText("");
						
						String customerIdToRemove = textFieldCustomerId.getText();
						ICustomer customerToRemove = new Customer();
						
						Pattern idPattern = Pattern.compile("^C\\d\\d\\d\\d$");
						Matcher matchId = idPattern.matcher(customerIdToRemove);
						
						boolean matchFoundId = matchId.find();
						
						if (customerIdToRemove.isEmpty()) {
							JOptionPane.showMessageDialog(null, "ID field is empty.");
						} else if (!matchFoundId) {
							JOptionPane.showMessageDialog(null, "Infomed ID is not in the right format"
									+ "\nFormat: 'C' followed by four digits\nExample: C9999.");
						} else {
							try {
								customerToRemove = bm1.searchCustomer(customerIdToRemove);
							} catch (NumberFormatException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (InsufficientFundsException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if (customerToRemove != null) {
								try {
									int question = JOptionPane.showConfirmDialog(null, "Are you sure do you want to remove customer?\n"+
											customerToRemove, "Customer about to be removed!!!", 
											JOptionPane.YES_NO_OPTION);
									if (question == JOptionPane.YES_OPTION) {
									    JOptionPane.showMessageDialog(null, "Customer Removed");
										bm1.removeCustomer(customerToRemove);

									} else {
									    JOptionPane.showMessageDialog(null, "Removal cancelled");
									}
									
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} else {
							    JOptionPane.showMessageDialog(null, "Cutomer not found");
							}
						}								
					}
				});
				btnRemoveCustomer.setBounds(474, 150, 165, 28);
				panelAddRemoveCustomer.add(btnRemoveCustomer);
			}
			
			JLabel lblCustomerEmail = new JLabel("Customer Email:");
			lblCustomerEmail.setHorizontalAlignment(SwingConstants.RIGHT);
			lblCustomerEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblCustomerEmail.setBounds(21, 192, 168, 28);
			panelAddRemoveCustomer.add(lblCustomerEmail);
			
			textFieldEmail = new JTextField();
			textFieldEmail.setColumns(10);
			textFieldEmail.setBounds(199, 198, 188, 20);
			panelAddRemoveCustomer.add(textFieldEmail);
			
			JLabel lblCustomerPhone = new JLabel("Customer Phone:");
			lblCustomerPhone.setHorizontalAlignment(SwingConstants.RIGHT);
			lblCustomerPhone.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblCustomerPhone.setBounds(21, 236, 168, 28);
			panelAddRemoveCustomer.add(lblCustomerPhone);
			
			textFieldPhone = new JTextField();
			textFieldPhone.setColumns(10);
			textFieldPhone.setBounds(199, 242, 188, 20);
			panelAddRemoveCustomer.add(textFieldPhone);
			
			
		}
		{
			final JPanel panelDisplayCustomersFromDataBase = new JPanel();
			// This panel is used to store the functions to read customers from file
			tabbedPane.addTab("Read Customers From File", null, panelDisplayCustomersFromDataBase, null);
			panelDisplayCustomersFromDataBase.setLayout(null);
			
			JButton btnNewButton_1 = new JButton("Read From File");
			// This block is used to perform the action of read the customers list from file
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					try {
						String newLabel = CustomersList.readFromFile().toString();
						JTextArea textAreaNewLabel = new JTextArea(20,30);
						textAreaNewLabel.append(newLabel);
						JScrollPane scrollPaneNewLabel = new JScrollPane(textAreaNewLabel);
						scrollPaneNewLabel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
						JOptionPane.showMessageDialog(null, scrollPaneNewLabel);						
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}									
				}
			});
			btnNewButton_1.setBounds(268, 125, 160, 35);
			panelDisplayCustomersFromDataBase.add(btnNewButton_1);		
		}
		{

			final JPanel panelReadCustomersFromFile = new JPanel();
			tabbedPane.addTab("Display Customers From DB", null, panelReadCustomersFromFile, null);

			final String[] columnNames = {"Customer ID","First Name","Last Name","PIN"};
					
			JButton btnShowCustomers = new JButton("Show Customers");
			btnShowCustomers.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					try {
						if(panelReadCustomersFromFile.isAncestorOf(table)) {
							panelReadCustomersFromFile.remove(table);
							panelReadCustomersFromFile.remove(table.getTableHeader());
						}
						ArrayList<ICustomer> listCustomerFromDb = CustomerDB.selectAll();
						
						Object[][] array = new Object[listCustomerFromDb.size()][4];
						int i = 0;
						for (ICustomer element : listCustomerFromDb) {
							array[i][0] = listCustomerFromDb.get(i).getUserId();
							array[i][1] = listCustomerFromDb.get(i).getfName();
							array[i][2] = listCustomerFromDb.get(i).getlName();
							array[i][3] = listCustomerFromDb.get(i).getPin();
							i++;
						}
						
						table = new JTable(array,columnNames);
						
						panelReadCustomersFromFile.add(table.getTableHeader());	
						panelReadCustomersFromFile.add(table);					
						panelReadCustomersFromFile.revalidate();
						panelReadCustomersFromFile.repaint();
						
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InsufficientFundsException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}				
				}
			});
			panelReadCustomersFromFile.add(btnShowCustomers);
			
		}
		
		JPanel panelModifyCustomer = new JPanel();
		tabbedPane.addTab("Modify Customer", null, panelModifyCustomer, null);
		panelModifyCustomer.setLayout(null);
		
		JLabel lblCustomerId_1 = new JLabel("Customer ID:");
		lblCustomerId_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCustomerId_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblCustomerId_1.setBounds(225, 64, 118, 30);
		panelModifyCustomer.add(lblCustomerId_1);
		
		textFieldCustomerId_2 = new JTextField();
		textFieldCustomerId_2.setBounds(356, 70, 96, 20);
		panelModifyCustomer.add(textFieldCustomerId_2);
		textFieldCustomerId_2.setColumns(10);
		
		JLabel lblModifyCustomer = new JLabel("Modify Customer");
		lblModifyCustomer.setHorizontalAlignment(SwingConstants.CENTER);
		lblModifyCustomer.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblModifyCustomer.setBounds(10, 23, 677, 30);
		panelModifyCustomer.add(lblModifyCustomer);
		
		JButton btnModifyCustomer = new JButton("Submit");
		btnModifyCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
				String customerId_2 = textFieldCustomerId_2.getText();
				final String customerOldPin = textFieldOldPin.getText();
				final String customerNewPin = textFieldCustomerNewPin.getText();
				final String confirmCustomerNewPin = textFieldConfirmCustomerNewPin.getText();

				ICustomer customerToChange = null;
				
				try {
					customerToChange = bm1.searchCustomer(customerId_2);
				} catch (NumberFormatException e2) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Number format exception");
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "SQL Exception");
				} catch (InsufficientFundsException e2) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Insufficient Funds Exception");
				}
				
				Pattern customerId2Pattern = Pattern.compile("^C\\d\\d\\d\\d$");
				
				Matcher matchCustomerId2 = customerId2Pattern.matcher(customerId_2);
								
				boolean matchFoundCustomerId2 = matchCustomerId2.find();				
				boolean matchFoundCustomerOldPin = false;
				boolean matchFoundCustomerNewPin = false;
				boolean matchFoundConfirmCustomerNewPin = false;
				
				try {
					matchFoundCustomerOldPin = Validator.isInvalidPIN(customerOldPin);
				} catch (NotInRangeException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Not in range exception");
				}
				
				try {
					matchFoundCustomerNewPin = Validator.isInvalidPIN(customerNewPin);
				} catch (NotInRangeException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Not in range exception");
				}
				
				try {
					matchFoundConfirmCustomerNewPin = Validator.isInvalidPIN(confirmCustomerNewPin);
				} catch (NotInRangeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (customerId_2.isEmpty() || customerOldPin.isEmpty() || customerNewPin.isEmpty() || confirmCustomerNewPin.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Some field is empty.");
				} else if (!matchFoundCustomerId2) {
					JOptionPane.showMessageDialog(null, "Invalid format for Customer Id.\n"
							+ "Must be 'C' followed by 4 digits.\nExample: C9999.");
				} else if (matchFoundCustomerOldPin  || matchFoundCustomerNewPin || matchFoundConfirmCustomerNewPin) {
					JOptionPane.showMessageDialog(null, "Invalid format for PIN.\n"
							+ "Must be 4 digits.");
				} else if (customerToChange == null) {
					JOptionPane.showMessageDialog(null, "Customer not found");
				} else if (!customerNewPin.equals(confirmCustomerNewPin) ) {
					JOptionPane.showMessageDialog(null, "Informed PIN are different");
				} else if (!customerOldPin.equals(customerToChange.getPin())) {
					JOptionPane.showMessageDialog(null, "Informed PIN are different");
				} else {
					try {
						((User) customerToChange).setPin(customerNewPin);
						CustomerDB.update(customerToChange);
						JOptionPane.showMessageDialog(null, "User's " + customerToChange.getfName() + " " +
						customerToChange.getlName() + " pin changed successfully.");
					} catch (NotInRangeException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
//				ICustomer modifiedCustomer = bm1.searchCustomer(cId);
//				ICustomer updatedCustomer = modifiedCustomer;
			}
		});
		btnModifyCustomer.setBounds(300, 223, 89, 30);
		panelModifyCustomer.add(btnModifyCustomer);
		
		JLabel lblCustomerOldPin = new JLabel("Customer Old Pin:");
		lblCustomerOldPin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCustomerOldPin.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblCustomerOldPin.setBounds(161, 99, 182, 30);
		panelModifyCustomer.add(lblCustomerOldPin);
		
		textFieldOldPin = new JTextField();
		textFieldOldPin.setColumns(10);
		textFieldOldPin.setBounds(356, 105, 96, 20);
		panelModifyCustomer.add(textFieldOldPin);
		
		JLabel lblCustomerNewPin = new JLabel("Customer New Pin:");
		lblCustomerNewPin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCustomerNewPin.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblCustomerNewPin.setBounds(161, 134, 182, 30);
		panelModifyCustomer.add(lblCustomerNewPin);
		
		textFieldCustomerNewPin = new JTextField();
		textFieldCustomerNewPin.setColumns(10);
		textFieldCustomerNewPin.setBounds(356, 140, 96, 20);
		panelModifyCustomer.add(textFieldCustomerNewPin);
		
		textFieldConfirmCustomerNewPin = new JTextField();
		textFieldConfirmCustomerNewPin.setColumns(10);
		textFieldConfirmCustomerNewPin.setBounds(356, 175, 96, 20);
		panelModifyCustomer.add(textFieldConfirmCustomerNewPin);
		
		JLabel lblConfirmCustomerNewPin = new JLabel("Confirm Customer New Pin:");
		lblConfirmCustomerNewPin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConfirmCustomerNewPin.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblConfirmCustomerNewPin.setBounds(129, 169, 214, 30);
		panelModifyCustomer.add(lblConfirmCustomerNewPin);
		{
			JPanel panelAddRemoveAccount = new JPanel();
			// Here we start the panel where we add or remove account
			tabbedPane.addTab("Add / Remove Account", null, panelAddRemoveAccount, null);
			panelAddRemoveAccount.setLayout(null);
			
			final JRadioButton rdbtnSavings = new JRadioButton("Savings");
			rdbtnSavings.setFont(new Font("Tahoma", Font.PLAIN, 16));
			rdbtnSavings.setBounds(24, 161, 169, 23);
			panelAddRemoveAccount.add(rdbtnSavings);
			
			rdbtnSavings.setSelected(true);
			
			final JRadioButton rdbtnCurrency = new JRadioButton("Currency");
			rdbtnCurrency.setFont(new Font("Tahoma", Font.PLAIN, 16));
			rdbtnCurrency.setBounds(24, 187, 110, 23);
			panelAddRemoveAccount.add(rdbtnCurrency);
			
			final JRadioButton rdbtnLineOfCredit = new JRadioButton("Line Of Credit");
			rdbtnLineOfCredit.setFont(new Font("Tahoma", Font.PLAIN, 16));
			rdbtnLineOfCredit.setBounds(24, 213, 140, 23);
			panelAddRemoveAccount.add(rdbtnLineOfCredit);
			
			final JRadioButton rdbtnCredit = new JRadioButton("Credit");
			rdbtnCredit.setFont(new Font("Tahoma", Font.PLAIN, 16));
			rdbtnCredit.setBounds(24, 239, 169, 23);
			panelAddRemoveAccount.add(rdbtnCredit);
			
			JLabel lblChooseAccount = new JLabel("Choose an Account:");
			lblChooseAccount.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblChooseAccount.setBounds(24, 122, 149, 32);
			panelAddRemoveAccount.add(lblChooseAccount);
			{
				JLabel lblCustomerId = new JLabel("Customer ID:");
				lblCustomerId.setHorizontalAlignment(SwingConstants.RIGHT);
				lblCustomerId.setFont(new Font("Tahoma", Font.PLAIN, 16));
				lblCustomerId.setBounds(54, 22, 110, 32);
				panelAddRemoveAccount.add(lblCustomerId);
			}
			{
				JLabel lblOpenDate = new JLabel("Open Date:");
				lblOpenDate.setHorizontalAlignment(SwingConstants.RIGHT);
				lblOpenDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
				lblOpenDate.setBounds(54, 94, 110, 32);
				panelAddRemoveAccount.add(lblOpenDate);
			}
			
			textFieldCustomerId_1 = new JTextField();
			textFieldCustomerId_1.setBounds(174, 30, 169, 20);
			panelAddRemoveAccount.add(textFieldCustomerId_1);
			textFieldCustomerId_1.setColumns(10);
			
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
			final JFormattedTextField formattedTextFieldOpenDate = new JFormattedTextField(dateFormat);
			formattedTextFieldOpenDate.setText("08-01-2007");
			formattedTextFieldOpenDate.setBounds(174, 102, 169, 20);
			panelAddRemoveAccount.add(formattedTextFieldOpenDate);
			
			JButton btnRemoveAccount = new JButton("Remove Account");
			btnRemoveAccount.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					String customerId2 = textFieldCustomerId_1.getText();
					
					Pattern customerId2Pattern = Pattern.compile("^C\\d\\d\\d\\d$");
					Matcher matchCustomerId2 = customerId2Pattern.matcher(customerId2);
					
					boolean matchFoundCustomerId2 = matchCustomerId2.find();
					
					if (customerId2.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Some field is empty.");
					} else if (!matchFoundCustomerId2) {
						JOptionPane.showMessageDialog(null, "Customer ID is in wrong format\n"
								+ "Must be 'C' followed by four digits\nExample: C9999");
					} else {
						try {
							ICustomer customerToDelAccount = bm1.searchCustomer(customerId2);
							String accountNumber = textFieldAccountNumber.getText();
							IAccount checkAccount = AccountDB.search(customerToDelAccount, accountNumber);

							if (customerToDelAccount != null && accountNumber != null && checkAccount != null) {
								bm1.removeAccount(customerToDelAccount, accountNumber);
								JOptionPane.showMessageDialog(null, "Account removed successfully.");
							} else if (customerToDelAccount == null && accountNumber == null) {
								JOptionPane.showMessageDialog(null, "Some field is empty.");
							} else {
								JOptionPane.showMessageDialog(null, "Customer not found or account not found.");
							}
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InsufficientFundsException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}					
				}
			});
			btnRemoveAccount.setBounds(454, 161, 157, 23);
			panelAddRemoveAccount.add(btnRemoveAccount);
			
			{
				JLabel lblAccountNumber = new JLabel("Account Number:");
				lblAccountNumber.setHorizontalAlignment(SwingConstants.RIGHT);
				lblAccountNumber.setFont(new Font("Tahoma", Font.PLAIN, 16));
				lblAccountNumber.setBounds(10, 59, 154, 32);
				panelAddRemoveAccount.add(lblAccountNumber);
			}
			{
				textFieldAccountNumber = new JTextField();
				textFieldAccountNumber.setColumns(10);
				textFieldAccountNumber.setBounds(174, 67, 169, 20);
				panelAddRemoveAccount.add(textFieldAccountNumber);
			}
			
			String[] currencies = {"BRL","EUR","USD","JPY"};
			final JComboBox comboBoxCurrency = new JComboBox(currencies);
			comboBoxCurrency.setBounds(154, 189, 84, 22);
			panelAddRemoveAccount.add(comboBoxCurrency);
			comboBoxCurrency.setVisible(false);

			rdbtnSavings.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rdbtnCurrency.setSelected(false);
					rdbtnLineOfCredit.setSelected(false);
					rdbtnCredit.setSelected(false);
					comboBoxCurrency.setVisible(false);

				}
			});
			
			rdbtnCurrency.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rdbtnSavings.setSelected(false);
					rdbtnLineOfCredit.setSelected(false);
					rdbtnCredit.setSelected(false);
					comboBoxCurrency.setVisible(true);
				}
			});
			
			rdbtnLineOfCredit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rdbtnSavings.setSelected(false);
					rdbtnCurrency.setSelected(false);
					rdbtnCredit.setSelected(false);
					comboBoxCurrency.setVisible(false);

				}
			});
			
			rdbtnCredit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rdbtnSavings.setSelected(false);
					rdbtnCurrency.setSelected(false);
					rdbtnLineOfCredit.setSelected(false);
					comboBoxCurrency.setVisible(false);

				}
			});
			

			
			
			{
				JButton btnAddAccount = new JButton("Add Account");
				btnAddAccount.addActionListener(new ActionListener() {
					
					
					public void actionPerformed(ActionEvent e) {
						
						IAccount newAccount = null;
						String customerId1 = textFieldCustomerId_1.getText();
						final String accountNumber = textFieldAccountNumber.getText();
						final Date openedDate = new Date(formattedTextFieldOpenDate.getText());
						ICustomer customerToAddAccount = null;
						
						Pattern customerId1Pattern = Pattern.compile("^C\\d\\d\\d\\d$");
						Pattern accountIdPattern = Pattern.compile("^A\\d\\d\\d\\d$");
						Pattern datePattern = Pattern.compile("^[0-3][0-9]-[0-1]+[0-9]-\\d\\d\\d\\d$");
						Matcher matchCustomerId1 = customerId1Pattern.matcher(customerId1);
						Matcher matchAccountId = accountIdPattern.matcher(accountNumber);
						
						boolean matchFoundCustomerId1 = matchCustomerId1.find();
						boolean matchFoundAccountId = matchAccountId.find();
						
						if (customerId1.isEmpty() || accountNumber.isEmpty()) {
							JOptionPane.showMessageDialog(null, "Some field is empty");
						} else if(!matchFoundCustomerId1) {
							JOptionPane.showMessageDialog(null, "Customer ID is in wrong format\n"
									+ "Must be 'C' followed by four digits\nExample: C9999");
						} else if(!matchFoundAccountId) {
							JOptionPane.showMessageDialog(null, "Account number is in wrong format\n"
									+ "Must be 'A' followed by four digits\nExample: A9999");
						} else {
							try {
								customerToAddAccount = bm1.searchCustomer(customerId1);
							} catch (NumberFormatException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							} catch (InsufficientFundsException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							
							if (customerToAddAccount != null) {
								if(rdbtnSavings.isSelected()) {
									newAccount = new SavingsAccount(accountNumber,openedDate,0,0);
								}
								if(rdbtnCurrency.isSelected()) {
									String currencyTypeComboBox = (String)comboBoxCurrency.getSelectedItem();
									EnumCurrency currencyType = EnumCurrency.Undefined;
									
									double exrate = 0.0;
									
									if (currencyTypeComboBox == "BRL") {
										exrate = 0.27;
										currencyType = EnumCurrency.BRL;
									} else if (currencyTypeComboBox == "USD") {
										exrate = 1.35;
										currencyType = EnumCurrency.USD;
									} else if (currencyTypeComboBox == "JPY") {
										exrate = 0.0095;
										currencyType = EnumCurrency.JPY;
									} else if (currencyTypeComboBox == "EUR") {
										exrate = 1.47;
										currencyType = EnumCurrency.EUR;
									}
									
									try {
										newAccount = new CurrencyAccount(accountNumber,openedDate,0,0,currencyType,exrate);
									} catch (InsufficientFundsException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
								if(rdbtnLineOfCredit.isSelected()) {
									newAccount = new LineOfCreditAccount(accountNumber,openedDate,0,0);
								}
								if(rdbtnCredit.isSelected()) {
									newAccount = new CreditAccount(accountNumber,openedDate,0,0);
								}
								try {
									IAccount exists = AccountDB.search(customerToAddAccount, newAccount.getType());
									
									if (exists == null) {
										bm1.addAccount(customerToAddAccount, newAccount);
										JOptionPane.showMessageDialog(null, "Account added:\n" + 
										customerToAddAccount);
									} else {
										JOptionPane.showMessageDialog(null, "Account already exists");
									}
								} catch (NumberFormatException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (InsufficientFundsException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}												
					}
				});
				btnAddAccount.setBounds(454, 99, 157, 23);
				panelAddRemoveAccount.add(btnAddAccount);
			}
			
			JList list = new JList();
			list.setBounds(174, 213, 50, -21);
			panelAddRemoveAccount.add(list);
			
			
			
		}
		{
			JPanel panelSaveCustomersFromFile = new JPanel();
			// This one saves customers to file
			tabbedPane.addTab("Save Customers to File", null, panelSaveCustomersFromFile, null);
			panelSaveCustomersFromFile.setLayout(null);
			{
				JButton btnNewButton = new JButton("Safe to File");
				btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 16));
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						try {
							//ArrayList<ICustomer> listCustomerFromDb = CustomerDB.selectAll();
							CustomersList.setListCustomers(CustomerDB.selectAll());
							CustomersList.writeToFile();
							JOptionPane.showMessageDialog(null, "Customers List saved to file");
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InsufficientFundsException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				btnNewButton.setBounds(244, 93, 209, 69);
				panelSaveCustomersFromFile.add(btnNewButton);
			}
			{
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(10, 265, 401, -217);
				panelSaveCustomersFromFile.add(scrollPane);
			}
		}
		{
			JPanel panelSortCustomers = new JPanel();
			// Sorting panel
			tabbedPane.addTab("Sort Customers", null, panelSortCustomers, null);
			panelSortCustomers.setLayout(null);
			
			final ArrayList<ICustomer> listDBCustomers = CustomerDB.selectAll();
			
			JButton btnSortLastName = new JButton("Last Name");
			btnSortLastName.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					PredicateAlgorithm.sort(listDBCustomers, new PredicateLastName());
					
					String newLabel = listDBCustomers.toString();
					JTextArea textAreaNewLabel = new JTextArea(20,30);
					textAreaNewLabel.append(newLabel);
					JScrollPane scrollPaneNewLabel = new JScrollPane(textAreaNewLabel);
					scrollPaneNewLabel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
					JOptionPane.showMessageDialog(null, scrollPaneNewLabel);
					
				}
			});
			btnSortLastName.setBounds(495, 127, 111, 41);
			panelSortCustomers.add(btnSortLastName);
			
			JButton btnSortFirstName = new JButton("First Name");
			btnSortFirstName.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					PredicateAlgorithm.sort(listDBCustomers, new PredicateFirstName());
					
					String newLabel = listDBCustomers.toString();
					JTextArea textAreaNewLabel = new JTextArea(20,30);
					textAreaNewLabel.append(newLabel);
					JScrollPane scrollPaneNewLabel = new JScrollPane(textAreaNewLabel);
					scrollPaneNewLabel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
					JOptionPane.showMessageDialog(null, scrollPaneNewLabel);
				}
			});
			btnSortFirstName.setBounds(293, 127, 111, 41);
			panelSortCustomers.add(btnSortFirstName);
			
			
			JButton btnSortClientId = new JButton("Client ID");
			btnSortClientId.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					PredicateAlgorithm.sort(listDBCustomers, new PredicateCustomerId());
					
					String newLabel = listDBCustomers.toString();
					JTextArea textAreaNewLabel = new JTextArea(20,30);
					textAreaNewLabel.append(newLabel);
					JScrollPane scrollPaneNewLabel = new JScrollPane(textAreaNewLabel);
					scrollPaneNewLabel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
					JOptionPane.showMessageDialog(null, scrollPaneNewLabel);
					
				}
			});
			btnSortClientId.setBounds(91, 127, 111, 41);
			panelSortCustomers.add(btnSortClientId);
			
			JLabel lblSortCostumers = new JLabel("Choose your sorting criteria");
			lblSortCostumers.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblSortCostumers.setHorizontalAlignment(SwingConstants.CENTER);
			lblSortCostumers.setBounds(153, 11, 391, 41);
			panelSortCustomers.add(lblSortCostumers);
		}
	}
}
