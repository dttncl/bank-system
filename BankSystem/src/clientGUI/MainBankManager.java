package clientGUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import bus.BankManager;
import bus.Customer;
import bus.InsufficientFundsException;
import data.ManagerDB;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class MainBankManager {

	private JFrame frameMain;
	private JTextField textFieldYourId;
	private JTextField textFieldPassword;
	
	//BankManager bm1 = new BankManager("BM0001","1234",managerFirstName,managerLastName,"b@mail.com","123-456-3456");
	Customer c1 = new Customer();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainBankManager window = new MainBankManager();
					window.frameMain.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainBankManager() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameMain = new JFrame();
		frameMain.setTitle("Fortis Bank System");
		frameMain.setBounds(100, 100, 282, 375);
		frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameMain.getContentPane().setLayout(null);
		
		JLabel lblChooseRole = new JLabel("Manager Application");
		lblChooseRole.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseRole.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblChooseRole.setBounds(10, 15, 248, 40);
		frameMain.getContentPane().add(lblChooseRole);
		
		JLabel lblYourId = new JLabel("Your ID:");
		lblYourId.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourId.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblYourId.setBounds(67, 66, 133, 23);
		frameMain.getContentPane().add(lblYourId);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(67, 139, 133, 23);
		frameMain.getContentPane().add(lblPassword);
		
		textFieldYourId = new JTextField();
		textFieldYourId.setBounds(67, 104, 133, 20);
		frameMain.getContentPane().add(textFieldYourId);
		textFieldYourId.setColumns(10);
		
		textFieldYourId.setText("M0001");
		
		textFieldPassword = new JPasswordField(6);
		textFieldPassword.setColumns(6);
		//textFieldPassword.p
		textFieldPassword.setBounds(67, 177, 133, 20);
		frameMain.getContentPane().add(textFieldPassword);
		
		textFieldPassword.setText("1234");

		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSubmit.setBounds(67, 220, 133, 23);
		frameMain.getContentPane().add(btnSubmit);
		
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String id = textFieldYourId.getText();
				String pass = textFieldPassword.getText();
				
				Pattern idPattern = Pattern.compile("^M\\d\\d\\d\\d$");				
				Matcher matchId = idPattern.matcher(id);

				boolean matchFoundId = matchId.find();
				
				if (id.isEmpty() || pass.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Some field is empty");
				} else if (!matchFoundId) {
					JOptionPane.showMessageDialog(null, "Informed ID in wrong format\n"
							+ "Must be 'M' followed by four digits\n"
							+ "Example: BM9999");
				} else {
					
					BankManager bm1 = null;
					try {
						bm1 = ManagerDB.searchById(id);
					} catch (NumberFormatException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					if (bm1 != null) {
						ManagerGUI newWindow = null;
						try {
							newWindow = new ManagerGUI(bm1);
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
						newWindow.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Wrong credentials");
					}
				}
			}
		});
	}

	private void setBounds(int i, int j, int k, int l) {
		// TODO Auto-generated method stub
		
	}
}
