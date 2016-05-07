package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import SoftwareHouse.Activity;
import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.EmployeeAlreadyAssignedException;
import SoftwareHouse.ExceptionTypes.EmployeeMaxActivitiesReachedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;

public class AddEmployeeToActivity {
	Scheduler scheduler = null;

	// TODO Needs to check i user also has the list of activities

	/**
	 * Setup the test environment by initialising the schduler, login, creating
	 * a sample project and staff it with employees. Further, add an activity to
	 * the project
	 */
	@Before
	public void Setup() {
		scheduler = new Scheduler();
		TestTools.login(scheduler);
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

		if (!(project.addEmployee("JBS") && project.addEmployee("ELL") && project.addEmployee("AGC") && project.addEmployee("NR"))) {
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
			project.addAcitivity(activityName, activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void AddEmployeeToActivitySuccessTest() {
		// Get the activity and employee
		Activity activity = null;
		try {
			activity = scheduler.getActivity("Navision Stat", "Udvikling af brugerinterface");
		} catch (Exception e1) {
			Assert.fail();
		}
		Employee employee = null;
		try {
			employee = scheduler.getEmployeeFromInitials("NR");
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		}
		
		// test the number of employees and activities are correct before and after 
		int numberOfActivities = employee.getNumberOfActivities();
		assertFalse(employee.isAlreadyPartOfActivity(activity));
		assertEquals(activity.getAssignedEmployees().size(), 3);
		try {
			activity.addEmployee("NR");
		} catch (Exception e) {
			Assert.fail();
		}
		assertEquals(activity.getAssignedEmployees().size(), 4);
		assertEquals(numberOfActivities + 1, employee.getNumberOfActivities());
		assertTrue(employee.isAlreadyPartOfActivity(activity));
	}

	@Test
	public void AddEmployeeToActivityEmployeeNotAssignedToProjectTest() {
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
	public void AddEmployeeToActivityEmployeeNotFoundTest() {

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
	public void AddEmployeeToActivityEmployeeAssignedTooManyActivitiesTest() {
		for (int i = 1; i < Employee.MAX_ACTIVITIES + 1; i++) {
			addActivity(String.valueOf(i));
		}
		for (int i = 1; i < Employee.MAX_ACTIVITIES; i++) {
			Activity activity = null;
			try {
				activity = scheduler.getActivity("Navision Stat", String.valueOf(i));
			} catch (Exception e1) {
				Assert.fail();
			}
			try {
				activity.addEmployee("JBS");
			} catch (Exception e) {
				Assert.fail(e.getMessage());
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

	private void addActivity(String activityName) {
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
			project.forceAddAcitivity(activityName, activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void AddEmployeeToSameActivityTwiceTest() {
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