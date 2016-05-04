package GUI.DialogBoxes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import GUI.Tools;
import SoftwareHouse.Employee;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;

public class CreateProjectDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JScrollPane allEmployeesScrollBar;
	private JScrollPane assignedEmployeesScrollBar;
	private final Scheduler scheduler;
	private final List<Employee> assignedEmployees = new ArrayList<Employee>();
	private JLabel errorLabel;

	/**
	 * Create the dialog.
	 */
	public CreateProjectDialog(Scheduler scheduler) {
		this.scheduler = scheduler;
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 375, 617);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNavn = new JLabel("Navn:");
			lblNavn.setBounds(10, 11, 54, 14);
			contentPanel.add(lblNavn);
		}
		{
			JTextField projectNameTextField = new JTextField();
			projectNameTextField.setBounds(140, 8, 209, 20);
			contentPanel.add(projectNameTextField);
			projectNameTextField.setColumns(10);
		}
		{
			JLabel lblKundeNavn = new JLabel("Kunde navn:");
			lblKundeNavn.setBounds(10, 36, 84, 14);
			contentPanel.add(lblKundeNavn);
		}
		{
			JLabel lblDetaljeretBeskrivelse = new JLabel("Detaljeret beskrivelse:");
			lblDetaljeretBeskrivelse.setBounds(10, 61, 131, 14);
			contentPanel.add(lblDetaljeretBeskrivelse);
		}
		{
			JTextArea detailedTextTextArea = new JTextArea();
			detailedTextTextArea.setBounds(10, 86, 339, 108);
			contentPanel.add(detailedTextTextArea);
		}
		{
			allEmployeesScrollBar = new JScrollPane();
			allEmployeesScrollBar.setBounds(10, 205, 110, 306);
			contentPanel.add(allEmployeesScrollBar);
		}
		{
			assignedEmployeesScrollBar = new JScrollPane();
			assignedEmployeesScrollBar.setBounds(239, 205, 110, 306);
			contentPanel.add(assignedEmployeesScrollBar);
		}
		{
			JButton assignEmployeeButton = new JButton("Tilf\u00F8j");
			assignEmployeeButton.setBounds(130, 343, 99, 23);
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
						} catch (EmployeeNotFoundException e1) {
							errorLabel.setText(e1.getMessage());
						}
					}
				}
			});
		}
		{
			JButton unassignEmployeeButton = new JButton("Fjern");
			unassignEmployeeButton.setBounds(130, 377, 99, 23);
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
						} catch (EmployeeNotFoundException e1) {
							errorLabel.setText(e1.getMessage());
						}
					}
				}
			});
		}
		{
			JTextField CostumersNameTextField = new JTextField();
			CostumersNameTextField.setBounds(140, 33, 209, 20);
			contentPanel.add(CostumersNameTextField);
			CostumersNameTextField.setColumns(10);
		}
		{
			errorLabel = new JLabel("");
			errorLabel.setBounds(10, 522, 339, 14);
			contentPanel.add(errorLabel);
			errorLabel.setPreferredSize(new Dimension(180, 14));
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Opret projekt");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//TODO add code here to create project. is waiting for Lombre to finish his part
						try {
							
						} catch (Exception e2) {
							errorLabel.setText(e2.getMessage());
						}
					}
				});
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void loadInformation()
	{		
		List<Employee> employees = scheduler.getEmployeesContainingString("");
		employees = employees.stream()
							 .filter(x -> !assignedEmployees.contains(x))
							 .collect(Collectors.toList());
		allEmployeesScrollBar.setViewportView(Tools.createTableOfEmployees(employees));
		assignedEmployeesScrollBar.setViewportView(Tools.createTableOfEmployees(assignedEmployees));
	}
}
