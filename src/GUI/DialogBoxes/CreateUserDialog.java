package GUI.DialogBoxes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class CreateUserDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final JTextField initialsTextField;
	private final Scheduler scheduler;

	/**
	 * Create the dialog.
	 */
	public CreateUserDialog(Scheduler scheduler) {
		this.scheduler = scheduler;
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 328, 142);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		initialsTextField = new JTextField();
		initialsTextField.setBounds(66, 8, 86, 20);
		contentPanel.add(initialsTextField);
		initialsTextField.setColumns(10);
		
		JLabel lblInitialer = new JLabel("Initialer:");
		lblInitialer.setBounds(10, 11, 46, 14);
		contentPanel.add(lblInitialer);
		
		JLabel createEmployeeErrorLabel = new JLabel("");
		createEmployeeErrorLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		createEmployeeErrorLabel.setHorizontalAlignment(SwingConstants.LEFT);
		createEmployeeErrorLabel.setForeground(Color.RED);
		createEmployeeErrorLabel.setBounds(10, 36, 292, 14);
		contentPanel.add(createEmployeeErrorLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Opret bruger");
				//okButton.setActionCommand("Ok");
				buttonPane.add(okButton);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							scheduler.addEmployee(initialsTextField.getText());
							CreateUserDialog.this.dispatchEvent(new WindowEvent(
									CreateUserDialog.this, WindowEvent.WINDOW_CLOSING));
						} catch (Exception e1) {
							createEmployeeErrorLabel.setText(e1.getMessage());
						}
					}
				});
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				//cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}
}
