package clientGUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;

import bus.ICustomer;
import bus.InsufficientFundsException;
import data.CustomerDB;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class MainBankCustomer {

	private JFrame frame;
	private JTextField textFieldYourId;
	private JPasswordField passwordField;
	ICustomer currentCustomer = null;
	
	MainBankManager main = new MainBankManager();
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainBankCustomer window = new MainBankCustomer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainBankCustomer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 275, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCustomerApplication = new JLabel("Customer Application");
		lblCustomerApplication.setHorizontalAlignment(SwingConstants.CENTER);
		lblCustomerApplication.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCustomerApplication.setBounds(6, 11, 248, 40);
		frame.getContentPane().add(lblCustomerApplication);
		
		JLabel lblYourId = new JLabel("Your ID:");
		lblYourId.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourId.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblYourId.setBounds(64, 62, 133, 23);
		frame.getContentPane().add(lblYourId);
		
		textFieldYourId = new JTextField();
		textFieldYourId.setColumns(10);
		textFieldYourId.setBounds(64, 100, 133, 20);
		frame.getContentPane().add(textFieldYourId);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(64, 135, 133, 23);
		frame.getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField(6);
		passwordField.setColumns(6);
		passwordField.setBounds(64, 173, 133, 20);
		frame.getContentPane().add(passwordField);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String id = textFieldYourId.getText();
				String pass = passwordField.getText();		
				
				Pattern idPattern = Pattern.compile("^C\\d\\d\\d\\d$");				
				Matcher matchId = idPattern.matcher(id);

				boolean matchFoundId = matchId.find();
							
				if (id.isEmpty() || pass.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Some field is empty");
				} else if (!matchFoundId) {
					JOptionPane.showMessageDialog(null, "Informed ID in wrong format\n"
							+ "Must be 'C' followed by four digits\n"
							+ "Example: C9999");
				} else {
					try {
						currentCustomer = CustomerDB.search(id);

						if (currentCustomer.getUserId().equals(id) && currentCustomer.getPin().equals(pass)) {							
							ClientGUI newWindow = null;
							try {
								newWindow = new ClientGUI(currentCustomer);
							} catch (NumberFormatException  e1) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(null, "Number Format Exception");
								e1.printStackTrace();
							}
							newWindow.setVisible(true);
						} else if (currentCustomer == null) {
							JOptionPane.showMessageDialog(null, "Customer not found");
						} else {
							JOptionPane.showMessageDialog(null, "Wrong credentials");
						}
					} catch (NumberFormatException e2) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Number Format Exception");
						e2.printStackTrace();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "SQL Exception");
						e2.printStackTrace();
					} catch (InsufficientFundsException e2) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Insufficient Funds Exception");
						e2.printStackTrace();
					}					
				}
			}
		});

		btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSubmit.setBounds(64, 216, 133, 23);
		frame.getContentPane().add(btnSubmit);
	}
}
