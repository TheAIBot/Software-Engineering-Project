package GUI;

import java.awt.GridBagConstraints;
import java.awt.ScrollPane;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import SoftwareHouse.Employee;
import SoftwareHouse.Scheduler;

public class LoginWindow extends SuperPage {
	private JTextField searchBar = new JTextField();
	private ScrollPane employeeList = new ScrollPane();
	private JButton loginButton = new JButton("Login");
	
	public LoginWindow(Scheduler scheduler) {
		super(scheduler);
	}

	@Override
	public void initiatePage(GUIController controller) {
		page.add(searchBar,    Tools.createConstraint(0, 0, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 5, 1));
		page.add(employeeList, Tools.createConstraint(0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 5, 1));
		page.add(loginButton,  Tools.createConstraint(0, 2, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.NONE, 5, 1));
	}
	
	public void loadInformation()
	{
		String[] columnNames = {"Employee Initials"};

		List<Employee> employees = scheduler.getEmployees();
		Object[][] employeesAsATable = new Object[employees.size()][1];
		for(int i = employees.size() - 1; i >= 0 ; i--)
		{
			employeesAsATable[employeesAsATable.length - 1 - i][0] = employees.get(i).getInitials();
		}
		
		employeeList.removeAll();
		employeeList.add(new JTable(employeesAsATable, columnNames));
	}
}
