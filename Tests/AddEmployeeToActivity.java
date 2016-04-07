import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import SoftwareHouse.Activity;
import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeAlreadyAssignedException;
import SoftwareHouse.ExceptionTypes.EmployeeMaxActivitiesReachedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.MissingProjectException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;

public class AddEmployeeToActivity {
	Scheduler scheduler = null;
	
	@Before
	public void Setup()
	{
		scheduler = new Scheduler();
		try {
			scheduler.createProject("Navision Stat");
		} catch (Exception e) {
			Assert.fail();
		}
		
		Project project = null;
		try {
			project = scheduler.getProject("Navision Stat");
		} catch (Exception e) {
			Assert.fail();
		}
		assertEquals(project.getOpenActivities().size(), 0);
		
		try {
			scheduler.addEmployee("JBS");
			scheduler.addEmployee("ELL");
			scheduler.addEmployee("AGC");
			scheduler.addEmployee("NR");
		} catch (Exception e) {
			Assert.fail();
		}
		
		try {
			project.addEmployee("JBS");
			project.addEmployee("ELL");
			project.addEmployee("AGC");
			project.addEmployee("NR");
		} catch (EmployeeNotFoundException e1) {
			Assert.fail();
		}
		
		String activityName = "Udvikling af brugerinterface";
		String activityDetailedDescription = "oprettelsen af et brugerinterface for programmet";
		int expectedHours = 200;
		Calendar startDate = new GregorianCalendar();
		startDate.set(2016, 3, 16);
		Calendar endDate = new GregorianCalendar();
		endDate.set(2016, 4, 18);
		List<String> employeeInitials = new ArrayList<String>();
		employeeInitials.add("JBS");
		employeeInitials.add("ELL");
		employeeInitials.add("AGC");
		
		try {
			project.addAcitivity(activityName,	activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void AddEmployeeToActivitySuccessTest()
	{		
		Activity activity = null;
		try {
			activity = scheduler.getActivity("Navision Stat", "Udvikling af brugerinterface");
		} catch (Exception e1) {
			Assert.fail();
		}
		
		assertEquals(activity.getAssignedEmployees().size(), 3);
		try {
			activity.addEmployee("NR");
		} catch (Exception e) {
			Assert.fail();
		}
		assertEquals(activity.getAssignedEmployees().size(), 4);
	}

	@Test
	public void AddEmployeeToActivityEmployeeNotAssignedToProjectTest()
	{
		Activity activity = null;
		try {
			activity = scheduler.getActivity("Navision Stat", "Udvikling af brugerinterface");
		} catch (Exception e1) {
			Assert.fail();
		}
		
		try {
			scheduler.addEmployee("LSB");
		} catch (Exception e) {
			Assert.fail();
		}
		
		assertEquals(activity.getAssignedEmployees().size(), 3);
		try {
			activity.addEmployee("LSB");
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			assertEquals(e.getMessage(), "Employee does not exists or is not part of this project");
		} catch (EmployeeMaxActivitiesReachedException e) {
			Assert.fail();
		} catch (EmployeeAlreadyAssignedException e) {
			Assert.fail();
		}
	}

	@Test
	public void AddEmployeeToActivityEmployeeNotFoundTest()
	{
		
		Activity activity = null;
		try {
			activity = scheduler.getActivity("Navision Stat", "Udvikling af brugerinterface");
		} catch (Exception e1) {
			Assert.fail();
		}
		
		assertEquals(activity.getAssignedEmployees().size(), 3);
		try {
			activity.addEmployee("LSB");
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			assertEquals(e.getMessage(), "Employee does not exists or is not part of this project");
		} catch (EmployeeMaxActivitiesReachedException e) {
			Assert.fail();
		} catch (EmployeeAlreadyAssignedException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void AddEmployeeToActivityEmployeeAssignedTooManyActivitiesTest()
	{
		for (int i = 0; i < Employee.MAX_ACTIVITIES + 1; i++) {
			addActivity(String.valueOf(i));
		}
		for (int i = 0; i < Employee.MAX_ACTIVITIES; i++) {
			Activity activity = null;
			try {
				activity = scheduler.getActivity("Navision Stat", String.valueOf(i));
			} catch (Exception e1) {
				Assert.fail();
			}
			try {
				activity.addEmployee("JBS");
			} catch (Exception e) {
				Assert.fail();
			}
		}
		Activity activity = null;
		try {
			activity = scheduler.getActivity("Navision Stat", String.valueOf(Employee.MAX_ACTIVITIES));
		} catch (Exception e1) {
			Assert.fail();
		}
		try {
			activity.addEmployee("JBS");
			Assert.fail();
		} catch (EmployeeMaxActivitiesReachedException e) {
			assertEquals(e.getMessage(), "JBS has reached the max of 20 activities");
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		} catch (EmployeeAlreadyAssignedException e) {
			Assert.fail();
		}
		
	}
	
	private void addActivity(String activityName)
	{
		Project project = null;
		try {
			project = scheduler.getProject("Navision Stat");
		} catch (Exception e) {
			Assert.fail();
		}
		String activityDetailedDescription = "oprettelsen af et brugerinterface for programmet";
		int expectedHours = 200;
		Calendar startDate = new GregorianCalendar();
		startDate.set(2016, 3, 16);
		Calendar endDate = new GregorianCalendar();
		endDate.set(2016, 4, 18);
		List<String> employeeInitials = new ArrayList<String>();
		
		try {
			project.forceAddAcitivity(activityName,	activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void AddEmployeeToSameActivityTwiceTest()
	{
		Activity activity = null;
		try {
			activity = scheduler.getActivity("Navision Stat", "Udvikling af brugerinterface");
		} catch (Exception e1) {
			Assert.fail();
		}
		
		try {
			activity.addEmployee("NR");
		} catch (EmployeeAlreadyAssignedException e) {
			assertEquals(e.getMessage(), "NR is already assigned to this activity");
		} catch (EmployeeMaxActivitiesReachedException e) {
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		}
	}
}