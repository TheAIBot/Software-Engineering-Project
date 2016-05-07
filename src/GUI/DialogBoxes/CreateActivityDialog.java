package GUI.DialogBoxes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import GUI.Tools;
import GUI.BorderComponents.JBorderTextField;
import GUI.Listeners.TextChangedListener;
import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.TimePeriod;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeMaxActivitiesReachedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectManagerNotLoggedInException;

public class CreateActivityDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final JScrollPane allEmployeesScrollBar;
	private final JScrollPane assignedEmployeesScrollBar;
	private final Scheduler scheduler;
	private final List<Employee> assignedEmployees = new ArrayList<Employee>();
	private final JBorderTextField startDateTextField;
	private final JBorderTextField endDateTextField;
	private final JTextArea detailedTextTextArea;
	private final Project project;
	private JBorderTextField projectNameTextField;
	private JBorderTextField BudgettedTimeTextField;
	private JLabel errorLabel;

	/**
	 * Create the dialog.
	 */
	public CreateActivityDialog(Scheduler scheduler, Project project) {
		this.scheduler = scheduler;
		this.project = project;
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 722, 356);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNavn = new JLabel("Navn:");
			lblNavn.setBounds(10, 11, 54, 14);
			contentPanel.add(lblNavn);
		}
		{
			JLabel lblDetaljeretBeskrivelse = new JLabel("Detaljeret beskrivelse:");
			lblDetaljeretBeskrivelse.setBounds(10, 114, 131, 14);
			contentPanel.add(lblDetaljeretBeskrivelse);
		}
		{
			detailedTextTextArea = new JTextArea();
			detailedTextTextArea.setBorder(UIManager.getBorder("TextField.border"));
			detailedTextTextArea.setBounds(10, 139, 339, 108);
			contentPanel.add(detailedTextTextArea);
		}
		{
			allEmployeesScrollBar = new JScrollPane();
			allEmployeesScrollBar.setBounds(359, 8, 110, 239);
			contentPanel.add(allEmployeesScrollBar);
		}
		{
			assignedEmployeesScrollBar = new JScrollPane();
			assignedEmployeesScrollBar.setBounds(588, 8, 110, 239);
			contentPanel.add(assignedEmployeesScrollBar);
		}
		{
			JButton assignEmployeeButton = new JButton("Tilf\u00F8j");
			assignEmployeeButton.setBounds(479, 102, 99, 23);
			contentPanel.add(assignEmployeeButton);
			assignEmployeeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JTable table = (JTable)allEmployeesScrollBar.getViewport().getView();
					if (table.getSelectedRow() != -1) {
						String emplyeeInitials = (String)table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());	
						try {
							Employee employee = scheduler.getEmployeeFromInitials(emplyeeInitials);
							assignedEmployees.add(employee);
							loadInformation();
						} catch (Exception e1) { }
					}
				}
			});
		}
		{
			JButton unassignEmployeeButton = new JButton("Fjern");
			unassignEmployeeButton.setBounds(479, 136, 99, 23);
			contentPanel.add(unassignEmployeeButton);
			unassignEmployeeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JTable table = (JTable)assignedEmployeesScrollBar.getViewport().getView();
					if (table.getSelectedRow() != -1) {
						String emplyeeInitials = (String)table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());	
						try {
							Employee employee = scheduler.getEmployeeFromInitials(emplyeeInitials);
							assignedEmployees.remove(employee);
							loadInformation();
						} catch (EmployeeNotFoundException e1) { }
					}
				}
			});
		}
		{
			JLabel lblStartDato = new JLabel("Start dato:");
			lblStartDato.setBounds(10, 39, 65, 14);
			contentPanel.add(lblStartDato);
		}
		{
			JLabel lblSlutDato = new JLabel("Slut dato:");
			lblSlutDato.setBounds(10, 64, 65, 14);
			contentPanel.add(lblSlutDato);
		}
		{
			startDateTextField = new JBorderTextField();
			startDateTextField.setBounds(140, 36, 209, 20);
			contentPanel.add(startDateTextField);
			startDateTextField.setColumns(10);
			startDateTextField.getDocument().addDocumentListener(new TextChangedListener() {
				@Override
				public void textChanged() {
					checkStartDate();
				}
			});
		}
		{
			endDateTextField = new JBorderTextField();
			endDateTextField.setBounds(140, 61, 209, 20);
			contentPanel.add(endDateTextField);
			endDateTextField.setColumns(10);
			endDateTextField.getDocument().addDocumentListener(new TextChangedListener() {
				@Override
				public void textChanged() {
					checkEndDate();
				}
			});
		}
		
		projectNameTextField = new JBorderTextField();
		projectNameTextField.setEditable(true);
		projectNameTextField.setBounds(140, 8, 209, 20);
		contentPanel.add(projectNameTextField);
		projectNameTextField.makeBorderRed();
		projectNameTextField.getDocument().addDocumentListener(new TextChangedListener() {
			@Override
			public void textChanged() {
				checkProjectName();
			}
		});
		
		JLabel lblBudgetteretTid = new JLabel("Budgetteret tid:");
		lblBudgetteretTid.setBounds(10, 89, 110, 14);
		contentPanel.add(lblBudgetteretTid);
		
		BudgettedTimeTextField = new JBorderTextField();
		BudgettedTimeTextField.setBounds(140, 86, 209, 20);
		contentPanel.add(BudgettedTimeTextField);
		BudgettedTimeTextField.setColumns(10);
		{
			errorLabel = new JLabel("");
			errorLabel.setForeground(Color.RED);
			errorLabel.setBounds(10, 259, 688, 14);
			contentPanel.add(errorLabel);
		}
		BudgettedTimeTextField.getDocument().addDocumentListener(new TextChangedListener() {
			@Override
			public void textChanged() {
				checkBudgettedTime();
			}
		});
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Opret aktivitet");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							tryCreateActivity();
							CreateActivityDialog.this.dispatchEvent(new WindowEvent(CreateActivityDialog.this, WindowEvent.WINDOW_CLOSING));
						} catch (Exception e2) {	
							errorLabel.setText(e2.getMessage());
						}
					}
				});
			}
			{
				JButton cancelButton = new JButton("Annuller oprettelse");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private void checkProjectName()
	{
		if (projectNameTextField.getText().trim().length() == 0) {
			projectNameTextField.makeBorderRed();
		} else {
			try {
				scheduler.getProject(projectNameTextField.getText());
				projectNameTextField.makeBorderRed();
				errorLabel.setText("A project with that name already exist");
			} catch (Exception e) {
				projectNameTextField.makeBorderGreen();
			}
		}
	}
	
	
	private void checkCostumerName()
	{
		
	}
	
	private void checkStartDate()
	{
		Tools.changeBorder(startDateTextField, x -> Tools.getCalendarFromString(x));
		Tools.changeBorder(endDateTextField, x -> Tools.getCalendarFromString(x));
	}
	
	private void checkEndDate()
	{
		Tools.changeBorder(startDateTextField, x -> Tools.getCalendarFromString(x));
		Tools.changeBorder(endDateTextField, x -> Tools.getCalendarFromString(x));
	}
	
	private void checkBudgettedTime()
	{
		Tools.changeBorder(BudgettedTimeTextField, x -> Integer.parseUnsignedInt(x));
	}
	
	private void checkDetailedText()
	{
	}
		
	
	private void tryCreateActivity() throws ParseException, EmployeeNotFoundException, DuplicateNameException, EmployeeMaxActivitiesReachedException, MissingInformationException, InvalidInformationException, ProjectManagerNotLoggedInException 
	{
		String activityName = projectNameTextField.getText();
		Calendar startDate = null;
		Calendar endDate = null;
		if (startDateTextField.getText().trim().length() != 0 &&
			endDateTextField.getText().trim().length() != 0) {
			startDate = Tools.getCalendarFromString(startDateTextField.getText());
			endDate = Tools.getCalendarFromString(endDateTextField.getText());
			//TODO fix this oddness that lombre mentioned
		}
		int budgettedTime = 0;
		if (BudgettedTimeTextField.getText().trim().length() != 0) {
			budgettedTime = Integer.parseUnsignedInt(BudgettedTimeTextField.getText());
		}
		String detailedDescription = detailedTextTextArea.getText();
		List<String> employeesInitials = assignedEmployees.stream().map(x -> x.getInitials()).collect(Collectors.toList());
		try {
			project.addAcitivity(activityName, detailedDescription, employeesInitials, startDate, endDate, budgettedTime);
		} catch (Exception e) {
			int dialogResult = JOptionPane.showConfirmDialog(null, "Vil du oprette denne ufærdige aktivitet?");
			if (dialogResult == JOptionPane.YES_OPTION) {
				project.forceAddAcitivity(activityName, detailedDescription, employeesInitials, startDate, endDate, budgettedTime);
			} else {
				project.addAcitivity(activityName, detailedDescription, employeesInitials, startDate, endDate, budgettedTime);
			}
		}
	}
	
	public void loadInformation()
	{	
		List<Employee> employees = project.getEmployees();
		employees = employees.stream()
							 .filter(x -> !assignedEmployees.contains(x))
							 .collect(Collectors.toList());
		allEmployeesScrollBar.setViewportView(Tools.createTableOfEmployees(employees));
		assignedEmployeesScrollBar.setViewportView(Tools.createTableOfEmployees(assignedEmployees));
	}
}
