package GUI;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.swing.JTable;

import GUI.BorderComponents.ColoredBorder;
import SoftwareHouse.Employee;
import SoftwareHouse.ThrowingLambdaInterfaces.ThrowingConsumer;

public class Tools {
	
	public static final String DATE_FORMAT = "dd MM yyyy";
	
	//copied from our ealier projects we wrote the code that this is copied from!!!
 	public static GridBagConstraints createConstraint(int gridx, int gridy, int gridWidth, int gridHeight, int anchor, int fill, int border, int weight)
	{
		return createConstraint(gridx, gridy, gridWidth, gridHeight, anchor, fill, border, weight, 0, 0);
	}
	
	public static GridBagConstraints createConstraint(int gridx, int gridy, int gridWidth, int gridHeight, int anchor, int fill, int border, int weight, int ipadx, int ipadY)
	{
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
	
	public static JTable createTableOfEmployees(List<Employee> employees)
	{
		final String[] columnNames = {"Employee Initials"};
		final Object[][] employeesAsATable = new Object[employees.size()][1];
		for(int i = employees.size() - 1; i >= 0 ; i--)
		{
			employeesAsATable[employeesAsATable.length - 1 - i][0] = employees.get(i).getInitials();
		}
		
		return new JTable(employeesAsATable, columnNames);
	}

	public static GregorianCalendar getCalendarFromString(String dateString) throws ParseException
	{
		DateFormat dateFormat = new SimpleDateFormat("DD MM yyyy");
		Date date = dateFormat.parse(dateString);
		GregorianCalendar dateCalendar = new GregorianCalendar();
		dateCalendar.setTime(date);
		return dateCalendar;
	}

	public static String changeBorder(ColoredBorder borderComponent, ThrowingConsumer<String> func)
	{
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
