package Tests;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.EmployeeAlreadyAssignedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.ProjectManagerNotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectManagerNotPartOfEmployeesAdded;

/**
 * @author ELL
 * Test the generation of a report
 */
public class TestFollowUp {

	Scheduler scheduler;
	
	/**
	 * Setup the test environment
	 */
	@Before
	public void setup() {
		scheduler = new Scheduler();
		TestTools.login(scheduler);
		// Create employees
		try {
			scheduler.addEmployee("AGC");
			scheduler.addEmployee("ELL");
			scheduler.addEmployee("NR");
			scheduler.addEmployee("JBS");
		} catch (Exception e) {
			Assert.fail();
		}
		
		// Create project
		try {
			scheduler.createProject("Navision Stat?"); // The ?-sign is used on purpose to test file naming
		} catch (Exception e) {
			Assert.fail();
		}
		Project project = null;
		try {
			project = scheduler.getProject("Navision Stat?");
		} catch (Exception e1) {
			Assert.fail();
		}
		
		try {
			if(!(project.addEmployee("JBS") && project.addEmployee("ELL") &&	project.addEmployee("AGC") &&	project.addEmployee("NR"))){
				Assert.fail();
			}
		} catch (Exception e1) {
			Assert.fail(e1.getMessage());
		}
		
		// Add a project manager and log him in
		try {
			project.setProjectManager("AGC");
		} catch (ProjectManagerNotPartOfEmployeesAdded e2) {
			Assert.fail();
		} catch (ProjectManagerNotLoggedInException e) {
			Assert.fail();
		}
		try {
			scheduler.login("AGC");
		} catch (EmployeeNotFoundException e1) {
			Assert.fail();
		}
		
		// Create activities
		String activityName = "Brugerinterface";
		String activityDetailedDescription = "Udvikling af brugerinterface";
		int expectedHours = 150;
		Calendar startDate = new GregorianCalendar();
		startDate.set(2016, 3, 16);
		Calendar endDate = new GregorianCalendar();
		endDate.set(2016, 4, 18);
		List<String> employeeInitials = new ArrayList<String>();
		employeeInitials.add("AGC");
		employeeInitials.add("ELL");
		try {
			project.addAcitivity(activityName,	activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		activityName = "Rendering";
		activityDetailedDescription = "Udikling af Rendering Engine. Herunder shadows, textures, smooth lightning.";
		expectedHours = 150;
		startDate = new GregorianCalendar();
		startDate.set(2016, 3, 16);
		endDate = new GregorianCalendar();
		endDate.set(2016, 4, 18);
		employeeInitials = new ArrayList<String>();
		employeeInitials.add("NR");
		employeeInitials.add("JBS");
		try {
			project.addAcitivity(activityName,	activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testGenerateReportSucces() {
		Project project = null;
		try {
			project = scheduler.getProject("Navision Stat?");
		} catch (Exception e) {
			Assert.fail();
		}
		
		project.generateReport();
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileReader(project.getFilePath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Assert.fail();
		}
		
		// Read file and verify it is correct
		Assert.assertEquals("Activity name: Brugerinterface", scanner.nextLine());
		Assert.assertEquals("Bugeted time: 150", scanner.nextLine());
		Assert.assertEquals("Detailed text: Udvikling af brugerinterface", scanner.nextLine());
		Assert.assertEquals("Employee initials: AGC, ELL", scanner.nextLine());
		Assert.assertEquals("", scanner.nextLine());
		
		Assert.assertEquals("Activity name: Rendering", scanner.nextLine());
		Assert.assertEquals("Bugeted time: 150", scanner.nextLine());
		Assert.assertEquals("Detailed text: Udikling af Rendering Engine. Herunder shadows, textures, smooth lightning.", scanner.nextLine());
		Assert.assertEquals("Employee initials: NR, JBS", scanner.nextLine());
		Assert.assertEquals("", scanner.nextLine());
		Assert.assertEquals("", scanner.nextLine());
		Assert.assertFalse(scanner.hasNextLine());
		
	}
	
}
