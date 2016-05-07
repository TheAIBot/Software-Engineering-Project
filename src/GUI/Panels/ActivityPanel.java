package GUI.Panels;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ActivityPanel extends JPanel {
	private JLabel activityNameLabel;
	private JLabel budgettedTimeLabel;
	private JButton changeActivityButton;
	private JButton addEmployeesButton;
	private JScrollPane employeesScrollPane;
	private JLabel inProjectLabel;
	private JButton registerTimeButton;
	
	public ActivityPanel() {
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));
		setLayout(null);
		
		addEmployeesButton = new JButton("Tilf\u00F8j medarbejdere");
		addEmployeesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		addEmployeesButton.setEnabled(false);
		addEmployeesButton.setBounds(221, 537, 161, 52);
		add(addEmployeesButton);
		
		changeActivityButton = new JButton("\u00C6ndr aktivitet");
		changeActivityButton.setEnabled(false);
		changeActivityButton.setBounds(392, 537, 119, 52);
		add(changeActivityButton);
		
		JLabel lblAktivitetsNavn = new JLabel("Aktivitets navn:");
		lblAktivitetsNavn.setBounds(10, 11, 105, 14);
		add(lblAktivitetsNavn);
		
		JLabel lblNewLabel = new JLabel("I projekt:");
		lblNewLabel.setBounds(10, 61, 85, 14);
		add(lblNewLabel);
		
		employeesScrollPane = new JScrollPane();
		employeesScrollPane.setBounds(521, 11, 269, 578);
		add(employeesScrollPane);
		
		JLabel lblNewLabel_1 = new JLabel("Budgetteret tid:");
		lblNewLabel_1.setBounds(10, 36, 105, 14);
		add(lblNewLabel_1);
		
		activityNameLabel = new JLabel("");
		activityNameLabel.setBounds(105, 11, 173, 14);
		add(activityNameLabel);
		
		budgettedTimeLabel = new JLabel("");
		budgettedTimeLabel.setBounds(103, 36, 175, 14);
		add(budgettedTimeLabel);
		
		inProjectLabel = new JLabel("");
		inProjectLabel.setBounds(105, 61, 173, 14);
		add(inProjectLabel);
		
		registerTimeButton = new JButton("Registrer tid");
		registerTimeButton.setBounds(92, 537, 119, 52);
		add(registerTimeButton);
	}

	/**
	 * @return the activityNameLabel
	 */
	public JLabel getActivityNameLabel() {
		return activityNameLabel;
	}

	/**
	 * @return the budgettedTimeLabel
	 */
	public JLabel getBudgettedTimeLabel() {
		return budgettedTimeLabel;
	}

	/**
	 * @return the changeActivityButton
	 */
	public JButton getChangeActivityButton() {
		return changeActivityButton;
	}

	/**
	 * @return the addEmployeesButton
	 */
	public JButton getAddEmployeesButton() {
		return addEmployeesButton;
	}

	/**
	 * @return the employeesScrollPane
	 */
	public JScrollPane getEmployeesScrollPane() {
		return employeesScrollPane;
	}

	/**
	 * @return the inProjectLabel
	 */
	public JLabel getInProjectLabel() {
		return inProjectLabel;
	}

	/**
	 * @return the registerTimeButton
	 */
	public JButton getRegisterTimeButton() {
		return registerTimeButton;
	}
}
