package GUI.Panels;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import GUI.GUIController;

import java.awt.Color;

public class LoginPanel extends JPanel {
	private JTextField employeeSearchTextField;
	private JLabel loggedInAsLabel;
	private JScrollPane employeeList;
	private JButton loginButton;
	private JLabel loginErrorLabel;
	private JButton createNewEmployeeButton;

	/**
	 * Create the panel.
	 */
	public LoginPanel() {
		setPreferredSize(GUIController.DEFAULT_PANEL_SIZE);
		setMinimumSize(GUIController.DEFAULT_PANEL_SIZE);
		setMaximumSize(GUIController.DEFAULT_PANEL_SIZE);
		setLayout(null);
		
		employeeSearchTextField = new JTextField();
		employeeSearchTextField.setBounds(1060, 14, 128, 20);
		add(employeeSearchTextField);
		employeeSearchTextField.setColumns(10);
		
		loginButton = new JButton("Skift til bruger");
		loginButton.setBounds(1060, 566, 128, 23);
		add(loginButton);
		
		JLabel lblCurrentlyLoggedIn = new JLabel("Currently logged in as:");
		lblCurrentlyLoggedIn.setBounds(10, 14, 169, 14);
		add(lblCurrentlyLoggedIn);
		
		loggedInAsLabel = new JLabel("");
		loggedInAsLabel.setBounds(10, 34, 216, 14);
		add(loggedInAsLabel);
		
		employeeList = new JScrollPane();
		employeeList.setBounds(1060, 45, 128, 513);
		add(employeeList);
		
		loginErrorLabel = new JLabel("");
		loginErrorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		loginErrorLabel.setForeground(Color.RED);
		loginErrorLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		loginErrorLabel.setBounds(424, 532, 252, 23);
		add(loginErrorLabel);
		
		createNewEmployeeButton = new JButton("Opret ny bruger");
		createNewEmployeeButton.setBounds(920, 566, 128, 23);
		add(createNewEmployeeButton);

	}

	
	/**
	 * @return the employeeSearchTextField
	 */
	public JTextField getEmployeeSearchTextField() {
		return employeeSearchTextField;
	}

	
	/**
	 * @return the loggedInAsLabel
	 */
	public JLabel getLoggedInAsLabel() {
		return loggedInAsLabel;
	}

	
	/**
	 * @return the employeeList
	 */
	public JScrollPane getEmployeeList() {
		return employeeList;
	}


	/**
	 * @return the btnLogin
	 */
	public JButton getLoginButton() {
		return loginButton;
	}


	/**
	 * @return the loginErrorLabel
	 */
	public JLabel getLoginErrorLabel() {
		return loginErrorLabel;
	}


	/**
	 * @return the createNewEmployeeButton
	 */
	public JButton getCreateNewEmployeeButton() {
		return createNewEmployeeButton;
	}
}
