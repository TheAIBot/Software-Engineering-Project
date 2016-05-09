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
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import GUI.Tools;
import GUI.BorderComponents.JBorderTextField;
import GUI.Listeners.TextChangedListener;
import SoftwareHouse.Activity;
import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeMaxActivitiesReachedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.ProjectManagerNotLoggedInException;

/**
 * @author Andreas
 */
public class AddEmployeesToActivityDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final JScrollPane allEmployeesScrollBar;
	private final JScrollPane assignedEmployeesScrollBar;
	private final Scheduler scheduler;
	private List<Employee> assignedEmployees = new ArrayList<Employee>();
	private final Activity activity;
	private JLabel errorLabel;

	/**
	 * Create the dialog.
	 */
	public AddEmployeesToActivityDialog(Scheduler scheduler, Activity activity) {
		this.scheduler = scheduler;
		this.activity = activity;
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 373, 360);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			allEmployeesScrollBar = new JScrollPane();
			allEmployeesScrollBar.setBounds(10, 11, 110, 239);
			contentPanel.add(allEmployeesScrollBar);
		}
		{
			assignedEmployeesScrollBar = new JScrollPane();
			assignedEmployeesScrollBar.setBounds(239, 11, 110, 239);
			contentPanel.add(assignedEmployeesScrollBar);
		}
		{
			JButton assignEmployeeButton = new JButton("Tilf\u00F8j");
			assignEmployeeButton.setBounds(130, 105, 99, 23);
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
			unassignEmployeeButton.setBounds(130, 139, 99, 23);
			contentPanel.add(unassignEmployeeButton);
			{
				errorLabel = new JLabel("");
				errorLabel.setForeground(Color.RED);
				errorLabel.setBounds(10, 261, 339, 14);
				contentPanel.add(errorLabel);
			}
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
							tryAddEmployees();
							AddEmployeesToActivityDialog.this.dispatchEvent(new WindowEvent(AddEmployeesToActivityDialog.this, WindowEvent.WINDOW_CLOSING));
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
		
	
	private void tryAddEmployees() throws InvalidInformationException, ProjectManagerNotLoggedInException
	{
		int dialogResult = JOptionPane.showConfirmDialog(null, "Vil du foretage disse �ndringer?");
		if (dialogResult == JOptionPane.YES_OPTION) {
			activity.setAssignedEmployees(assignedEmployees); 
		}
	}
	
	public void loadInformation()
	{	
		assignedEmployees = activity.getAssignedEmployees();
		assignedEmployeesScrollBar.setViewportView(Tools.createTableOfEmployees(assignedEmployees, (a, b) -> b.getActivities().size() - a.getActivities().size()));
		
		List<Employee> employees = activity.getInProject().getEmployees();
		employees = employees.stream()
							 .filter(x -> !assignedEmployees.contains(x))
							 .collect(Collectors.toList());
		allEmployeesScrollBar.setViewportView(Tools.createTableOfEmployees(employees, (a, b) -> b.getActivities().size() - a.getActivities().size()));
	}
}
