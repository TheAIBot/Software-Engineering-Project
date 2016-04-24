package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

import SoftwareHouse.Activity;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;

/**
 * @author ELL
 */
public class TestGenerateReport {

	Scheduler scheduler;
	
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
			scheduler.createProject("Navision Stat?");
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
			project.addEmployee("AGC");
			project.addEmployee("ELL");
			project.addEmployee("NR");
			project.addEmployee("JBS");
		} catch (EmployeeNotFoundException e) {
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
	public void testEditActivitySucces() {
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
		Assert.assertEquals("Employee initials: AGC ELL ", scanner.nextLine());
		Assert.assertEquals("", scanner.nextLine());
		Assert.assertEquals("", scanner.nextLine());
		
		Assert.assertEquals("Activity name: Rendering", scanner.nextLine());
		Assert.assertEquals("Bugeted time: 150", scanner.nextLine());
		Assert.assertEquals("Detailed text: Udikling af Rendering Engine. Herunder shadows, textures, smooth lightning.", scanner.nextLine());
		Assert.assertEquals("Employee initials: NR JBS ", scanner.nextLine());
		Assert.assertEquals("", scanner.nextLine());
		Assert.assertEquals("", scanner.nextLine());
		Assert.assertFalse(scanner.hasNextLine());
		
	}
	
}
