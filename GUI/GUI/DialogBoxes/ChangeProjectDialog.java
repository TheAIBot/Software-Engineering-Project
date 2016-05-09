package GUI.DialogBoxes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.ArrayList;
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
import javax.swing.border.EmptyBorder;

import GUI.Tools;
import GUI.BorderComponents.JBorderTextField;
import GUI.Listeners.TextChangedListener;
import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.TimePeriod;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectManagerNotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectManagerNotPartOfEmployeesAdded;

/**
 * @author Niklas
 */
public class ChangeProjectDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final Scheduler scheduler;
	private final JBorderTextField startDateTextField;
	private final JBorderTextField endDateTextField;
	private final JBorderTextField projectManagerTextField;
	private final JTextField costumersNameTextField;
	private final JTextArea detailedTextTextArea;
	private final Project project;
	private JBorderTextField projectNameTextField;
	private JBorderTextField BudgettedTimeTextField;
	private JLabel errorLabel;

	/**
	 * Create the dialog.
	 */
	public ChangeProjectDialog(Scheduler scheduler, Project project) {
		this.scheduler = scheduler;
		this.project = project;
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 374, 400);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNavn = new JLabel("Navn:");
			lblNavn.setBounds(10, 11, 54, 14);
			contentPanel.add(lblNavn);
		}
		{
			JLabel lblKundeNavn = new JLabel("Kunde navn:");
			lblKundeNavn.setBounds(10, 36, 84, 14);
			contentPanel.add(lblKundeNavn);
		}
		{
			JLabel lblDetaljeretBeskrivelse = new JLabel("Detaljeret beskrivelse:");
			lblDetaljeretBeskrivelse.setBounds(10, 159, 131, 14);
			contentPanel.add(lblDetaljeretBeskrivelse);
		}
		{
			detailedTextTextArea = new JTextArea();
			detailedTextTextArea.setBorder(UIManager.getBorder("TextField.border"));
			detailedTextTextArea.setBounds(10, 184, 339, 108);
			contentPanel.add(detailedTextTextArea);
		}
		{
			costumersNameTextField = new JBorderTextField();
			costumersNameTextField.setBounds(140, 33, 209, 20);
			contentPanel.add(costumersNameTextField);
			costumersNameTextField.setColumns(10);
		}
		{
			JLabel lblStartDato = new JLabel("Start dato:");
			lblStartDato.setBounds(10, 61, 65, 14);
			contentPanel.add(lblStartDato);
		}
		{
			JLabel lblSlutDato = new JLabel("Slut dato:");
			lblSlutDato.setBounds(10, 86, 65, 14);
			contentPanel.add(lblSlutDato);
		}
		{
			JLabel lblProjektManager = new JLabel("Projekt manager:");
			lblProjektManager.setBounds(10, 134, 110, 14);
			contentPanel.add(lblProjektManager);
		}
		{
			startDateTextField = new JBorderTextField();
			startDateTextField.setBounds(140, 58, 209, 20);
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
			endDateTextField.setBounds(140, 83, 209, 20);
			contentPanel.add(endDateTextField);
			endDateTextField.setColumns(10);
			endDateTextField.getDocument().addDocumentListener(new TextChangedListener() {
				@Override
				public void textChanged() {
					checkEndDate();
				}
			});
		}
		{
			projectManagerTextField = new JBorderTextField();
			projectManagerTextField.setBounds(140, 133, 209, 20);
			contentPanel.add(projectManagerTextField);
			projectManagerTextField.setColumns(10);
			projectManagerTextField.getDocument().addDocumentListener(new TextChangedListener() {
				@Override
				public void textChanged() {
					checkProjectManagerInitials();
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
		lblBudgetteretTid.setBounds(10, 111, 110, 14);
		contentPanel.add(lblBudgetteretTid);
		
		BudgettedTimeTextField = new JBorderTextField();
		BudgettedTimeTextField.setBounds(140, 108, 209, 20);
		contentPanel.add(BudgettedTimeTextField);
		BudgettedTimeTextField.setColumns(10);
		{
			errorLabel = new JLabel("");
			errorLabel.setForeground(Color.RED);
			errorLabel.setBounds(10, 303, 339, 14);
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
				JButton okButton = new JButton("Foretag \u00E6ndringer");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							changeProject();
							ChangeProjectDialog.this.dispatchEvent(new WindowEvent(ChangeProjectDialog.this, WindowEvent.WINDOW_CLOSING));
						} catch (Exception e2) {	
							errorLabel.setText(e2.getMessage());
						}
					}
				});
			}
			{
				JButton cancelButton = new JButton("Annuller \u00E6ndringer");
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
			if (!projectNameTextField.getText().equals(project.getName())) {
				try {
					scheduler.getProject(projectNameTextField.getText());
					projectNameTextField.makeBorderRed();
					errorLabel.setText("A project with that name already exist");
				} catch (Exception e) {
					projectNameTextField.makeBorderGreen();
				}
			} else {
				projectNameTextField.makeBorderGreen();
			}
		}
	}
	
	
	private void checkCostumerName()
	{
	}
	
	private void checkStartDate()
	{
		Tools.changeBorder(startDateTextField, x -> TimePeriod.getCalendarFromString(x));
		Tools.changeBorder(endDateTextField, x -> TimePeriod.getCalendarFromString(x));
	}
	
	private void checkEndDate()
	{
		Tools.changeBorder(startDateTextField, x -> TimePeriod.getCalendarFromString(x));
		Tools.changeBorder(endDateTextField, x -> TimePeriod.getCalendarFromString(x));
	}
	
	private void checkBudgettedTime()
	{
		Tools.changeBorder(BudgettedTimeTextField, x -> Integer.parseUnsignedInt(x));
	}
	
	private void checkProjectManagerInitials()
	{
		if (projectManagerTextField.getText().trim().length() == 0) {
			projectManagerTextField.makeBorderDefaultColor();
		}
		if (project.getEmployees().stream().anyMatch(x -> x.getInitials().equals(projectManagerTextField.getText().trim()))) {
			projectManagerTextField.makeBorderGreen();
		} else {
			projectManagerTextField.makeBorderRed();
		}
	}
	
	private void checkDetailedText()
	{
	}
		
	
	private void changeProject() throws ParseException, NotLoggedInException, MissingInformationException, InvalidInformationException, EmployeeNotFoundException, DuplicateNameException, ProjectManagerNotPartOfEmployeesAdded, ProjectManagerNotLoggedInException
	{
		String projectName = projectNameTextField.getText();
		String costumerName = costumersNameTextField.getText();
		TimePeriod timePeriod = null;
		if (startDateTextField.getText().trim().length() != 0 &&
			endDateTextField.getText().trim().length() != 0) {
			GregorianCalendar startDate = TimePeriod.getCalendarFromString(startDateTextField.getText());
			GregorianCalendar endDate = TimePeriod.getCalendarFromString(endDateTextField.getText());
			timePeriod = new TimePeriod(startDate, endDate);
		}
		int budgettedTime = 0;
		if (BudgettedTimeTextField.getText().trim().length() != 0) {
			budgettedTime = Integer.parseUnsignedInt(BudgettedTimeTextField.getText());
		}

		String projectManagerInitials = projectManagerTextField.getText();
		String detailedDescription = detailedTextTextArea.getText();
		
		int dialogResult = JOptionPane.showConfirmDialog(null, "Vil du foretage disse ændringer?");
		if (dialogResult == JOptionPane.YES_OPTION) {
			if (!project.getName().equals(projectName)) {
				project.setName(projectName);
			}
			if (!SoftwareHouse.Tools.isNullOrEmpty(projectManagerInitials)) {
				project.setProjectManager(projectManagerInitials);
			}
			project.setCostumerName(costumerName);
			project.setTimePeriod(timePeriod);
			project.setBudgettedTime(budgettedTime);
			project.setDetailedText(detailedDescription);
		}
	}
	
	public void loadInformation()
	{		
		projectNameTextField.setText(project.getName());
		costumersNameTextField.setText(project.getCostumerName());
		if (project.getTimePeriod() != null) {
			startDateTextField.setText(project.getTimePeriod().getStartDateAsString());
			endDateTextField.setText(project.getTimePeriod().getEndDateAsString());
		}
		BudgettedTimeTextField.setText(String.valueOf(project.getBudgetedTime()));
		if (project.getProjectManager() != null) {
			projectManagerTextField.setText(project.getProjectManager().getInitials());
		}
		detailedTextTextArea.setText(project.getDetailedText());
	}
}
