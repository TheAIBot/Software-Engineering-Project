package GUI.DialogBoxes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import GUI.Tools;
import GUI.BorderComponents.JBorderTextField;
import GUI.Listeners.TextChangedListener;
import SoftwareHouse.Activity;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.EmployeeNotAffiliatedWithProjectException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RegisterTimeDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final Scheduler scheduler;
	private final Project project;
	private final Activity activity;
	private JBorderTextField hoursUsedTextField;
	private JTextArea detailedTextTextArea;

	/**
	 * Create the dialog.
	 */
	public RegisterTimeDialog(Scheduler scheduler, Project project, Activity activity) {
		this.scheduler = scheduler;
		this.project = project;
		this.activity = activity;
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 322);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Timer brugt:");
		lblNewLabel.setBounds(10, 11, 90, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Beskrivelse");
		lblNewLabel_1.setBounds(10, 36, 90, 14);
		contentPanel.add(lblNewLabel_1);
		
		detailedTextTextArea = new JTextArea();
		detailedTextTextArea.setBounds(10, 56, 414, 161);
		contentPanel.add(detailedTextTextArea);
		
		hoursUsedTextField = new JBorderTextField();
		hoursUsedTextField.setBounds(110, 8, 86, 20);
		contentPanel.add(hoursUsedTextField);
		hoursUsedTextField.setColumns(10);
		hoursUsedTextField.getDocument().addDocumentListener(new TextChangedListener() {
			@Override
			public void textChanged() {
				checkHoursUsed();
			}
		});
		
		JLabel errorLabel = new JLabel("");
		errorLabel.setBounds(10, 225, 414, 14);
		contentPanel.add(errorLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							tryRegisterTime();
							RegisterTimeDialog.this.dispatchEvent(new WindowEvent(RegisterTimeDialog.this, WindowEvent.WINDOW_CLOSING));
						} catch (Exception e2) {	
							errorLabel.setText(e2.getMessage());
						}
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private void checkHoursUsed() throws NumberFormatException
	{
		Tools.changeBorder(hoursUsedTextField, x -> Integer.parseUnsignedInt(x));
	}
	
	public void tryRegisterTime() throws ProjectNotFoundException, NotLoggedInException, ActivityNotFoundException, InvalidInformationException, EmployeeNotFoundException, EmployeeNotAffiliatedWithProjectException
	{
		int hoursUsed = Integer.valueOf(hoursUsedTextField.getText());
		String detailedDescription = detailedTextTextArea.getText();
		scheduler.getLoggedInEmployee().registerTime(project.getName(), activity.getName(), detailedDescription, hoursUsed);
		
	}
}
