package GUI;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.print.attribute.ResolutionSyntax;
import javax.swing.JTable;

import com.sun.istack.internal.FinalArrayList;

import GUI.BorderComponents.ColoredBorder;
import GUI.Pages.ActivityPage;
import GUI.Pages.ProjectPage;
import SoftwareHouse.Activity;
import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.TimePeriod;
import SoftwareHouse.ThrowingLambdaInterfaces.ThrowingConsumer;

/**
 * @author Andreas
 */
public class Tools {

	// The following two method are copied from an earlier project made by all
	// of us (excactly the same group as the currenct) in the course
	// "Introduktion til softwareteknologi" in January. Only copied to avoid
	// unecessary hazzles with a part of the layout

	public static GridBagConstraints createConstraint(int gridx, int gridy, int gridWidth, int gridHeight, int anchor, int fill, int border, int weight) {
		return createConstraint(gridx, gridy, gridWidth, gridHeight, anchor, fill, border, weight, 0, 0);
	}

	public static GridBagConstraints createConstraint(int gridx, int gridy, int gridWidth, int gridHeight, int anchor, int fill, int border, int weight, int ipadx, int ipadY) {
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = gridx;
		gc.gridy = gridy;
		gc.weightx = weight;
		gc.weighty = weight;
		gc.fill = fill;
		gc.insets = new Insets(border, border, border, border);
		gc.gridwidth = gridWidth;
		gc.gridheight = gridHeight;
		gc.anchor = anchor;
		gc.ipadx = ipadx;
		gc.ipady = ipadY;
		return gc;
	}

	/**
	 * @param employees
	 * @return JTable
	 */
	public static JTable createTableOfEmployees(List<Employee> employees) {
		return createTableOfEmployees(employees, (a, b) -> b.getInitials().compareTo(a.getInitials()));
	}
	
	public static JTable createTableOfEmployees(List<Employee> employees, Comparator<Employee> comparator) {
		Collections.sort(employees, comparator);
		final String[] columnNames = { "Employee Initials" };
		final Object[][] employeesAsATable = new Object[employees.size()][1];
		for (int i = employees.size() - 1; i >= 0; i--) {
			employeesAsATable[employeesAsATable.length - 1 - i][0] = employees.get(i).getInitials();
		}

		return new JTable(employeesAsATable, columnNames);
	}

	/**
	 * @param projects
	 * @param controller
	 * @param scheduler
	 * @return JTable
	 */
	public static JTable createTableOfProjects(List<Project> projects, GUIController controller, Scheduler scheduler) {
		final String[] columnNames = { "Projek navn", "Aktiviteter", "Medarbejdere" };
		Collections.sort(projects, (a, b) -> b.getName().compareTo(a.getName()));
		final Object[][] employeesAsATable = new Object[projects.size()][3];
		for (int i = projects.size() - 1; i >= 0; i--) {
			employeesAsATable[employeesAsATable.length - 1 - i][0] = projects.get(i).getName();
			employeesAsATable[employeesAsATable.length - 1 - i][1] = projects.get(i).getOpenActivities().size();
			employeesAsATable[employeesAsATable.length - 1 - i][2] = projects.get(i).getEmployees().size();
		}
		JTable projectTable = new JTable(employeesAsATable, columnNames);
		projectTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = projectTable.rowAtPoint(e.getPoint());
					String projectName = (String) projectTable.getValueAt(row, 0);
					try {
						controller.switchPage(new ProjectPage(controller, scheduler, scheduler.getProject(projectName)));
					} catch (Exception e1) {
					}
				}
			}
		});
		return projectTable;
	}

	/**
	 * @param activities
	 * @param controller
	 * @param scheduler
	 * @return JTable
	 */
	public static JTable createTableOfActivities(final List<Activity> activities, GUIController controller, Scheduler scheduler) {
		final String[] columnNames = { "Aktivitets navn", "Medarbejdere", "Projekt", "Budgetteret tid" };
		Collections.sort(activities, (a, b) -> b.getProjectName().compareTo(a.getProjectName()));
		final Object[][] employeesAsATable = new Object[activities.size()][4];
		for (int i = activities.size() - 1; i >= 0; i--) {
			employeesAsATable[employeesAsATable.length - 1 - i][0] = activities.get(i).getName();
			employeesAsATable[employeesAsATable.length - 1 - i][1] = activities.get(i).getAssignedEmployees().size();
			employeesAsATable[employeesAsATable.length - 1 - i][2] = activities.get(i).getProjectName();
			employeesAsATable[employeesAsATable.length - 1 - i][3] = activities.get(i).getBudgettedTime();
		}
		JTable activityTable = new JTable(employeesAsATable, columnNames);
		activityTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = activityTable.rowAtPoint(e.getPoint());
					try {
						controller.switchPage(new ActivityPage(controller, scheduler, activities.get(row)));
					} catch (Exception e1) {
					}
				}
			}
		});
		return activityTable;
	}

	/**
	 * @param dateString
	 * @return GregorianCalendar
	 * @throws ParseException
	 */
	public static GregorianCalendar getCalendarFromString(String dateString) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(TimePeriod.DATE_FORMAT);
		Date date = dateFormat.parse(dateString);
		GregorianCalendar dateCalendar = new GregorianCalendar();
		dateCalendar.setTime(date);
		return dateCalendar;
	}

	/**
	 * Change the border of the text field or combo box
	 * @param borderComponent
	 * @param func
	 * @return String
	 */
	public static String changeBorder(ColoredBorder borderComponent, ThrowingConsumer<String> func) {
		String errorText = "";
		try {
			String text = borderComponent.getText();
			if (text.trim().length() != 0) {
				func.accept(text);
				borderComponent.makeBorderGreen();
			} else {
				borderComponent.makeBorderDefaultColor();
			}
		} catch (Exception e2) {
			errorText = e2.getMessage();
			borderComponent.makeBorderRed();
		}
		return errorText;
	}
}
