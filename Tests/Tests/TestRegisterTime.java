package Tests;

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

import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.RegisteredTime;
import SoftwareHouse.Scheduler;
import SoftwareHouse.TimeVault;
import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;

public class TestRegisterTime {

	private Scheduler scheduler = null;

	@Before
	public void setup() {
		scheduler = new Scheduler();
		TestTools.login(scheduler);
		try {
			TestTools.addEmployee(scheduler, "AAAA");
			TestTools.createProject(scheduler, "Navision Stat");
			TestTools.addEmployeeToProject(scheduler, "AAAA", "Navision Stat");
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testRegisterTimeSuccess() throws ProjectNotFoundException, NotLoggedInException, ActivityNotFoundException {
		try {
			TestTools.addActivity(scheduler, "Navision Stat", "Brugerinterface", new String[] { "AAAA" });
		} catch (Exception e) {
			Assert.fail();
		}
		Employee employee = null;
		try {
			employee = scheduler.getEmployeeFromInitials("AAAA");
			employee.registerTime("Navision Stat", "Brugerinterface", "Gridbag layout færdigt sat op og justeret", 5);
		} catch (Exception e) {
			Assert.fail();
		}
		
		// Test that all local fields are correct
		RegisteredTime registeredTime = scheduler.getTimeVault().getEmployeeTime("AAAA").get(0);
		assertEquals(registeredTime.getMessage(), "Gridbag layout færdigt sat op og justeret");
		assertTrue(registeredTime.getEmployee() == employee);
		assertEquals(registeredTime.getTime(), 5);
		assertEquals(1, scheduler.getTimeVault().getEmployeeTime("AAAA").size());
		assertEquals(1, scheduler.getTimeVault().getProjectTime("Navision Stat").size());
		assertEquals(1, scheduler.getTimeVault().getActivityTime("Navision Stat", "Brugerinterface").size());
		assertTrue(registeredTime == scheduler.getTimeVault().getProjectTime("Navision Stat").get(0));
		assertTrue(registeredTime == scheduler.getTimeVault().getActivityTime("Navision Stat", "Brugerinterface").get(0));
	}
	
	@Test
	public void testRegisterTimeEmployeeNotAffiliatedWithProject() {
		Employee employee = null;
		try {
			employee = scheduler.getEmployeeFromInitials("AAAA");
			employee.registerTime("Navision Stat", "Brugerinterface", "Gridbag layout færdigt sat op og justeret", 3);
			Assert.fail();
		} catch (Exception e) {
		}
	}
	
	/**
	 * Time is invalid if it is negative
	 */
	@Test
	public void testRegisterTimeInvalidTime() {
		Employee employee = null;
		try {
			TestTools.addActivity(scheduler, "Navision Stat", "Brugerinterface", new String[] { "AAAA" });
			employee = scheduler.getEmployeeFromInitials("AAAA");
		} catch (Exception e) {
			Assert.fail();
		}
		
		try {
			employee.registerTime("Navision Stat", "Brugerinterface", "Gridbag layout færdigt sat op og justeret", -42);
			Assert.fail();
		} catch (Exception e) {
			assertEquals("Used time can't be less than 0", e.getMessage());
		}
	}
	
	/**
	 * Text is invalid if it is empty
	 */
	@Test
	public void testRegisterTimeInvalidText() {
		Employee employee = null;
		try {
			TestTools.addActivity(scheduler, "Navision Stat", "Brugerinterface", new String[] { "AAAA" });
			employee = scheduler.getEmployeeFromInitials("AAAA");
		} catch (Exception e) {
			Assert.fail();
		}
		
		try {
			employee.registerTime("Navision Stat", "Brugerinterface", "", 5);
			Assert.fail();
		} catch (Exception e) {
			assertEquals("Invalid text", e.getMessage());
		}
	}
	
	
//	@Test
//	public void RegisterTimeMissingOrInvalidInformationTest() {
//		Employee employee = null;
//		try {
//			TestTools.addActivity(scheduler, "Derp", "add fish", new String[] { "JSB" });
//		} catch (Exception e) {
//			Assert.fail();
//		}
//		
//		try {
//			employee = scheduler.getEmployeeFromInitials("JSB");
//		} catch (Exception e) {
//			Assert.fail();
//		}
//
//		try {
//			employee.registerTime(null, "add fish", "did stuff", 3);
//			Assert.fail();
//		} catch (ProjectNotFoundException e) {
//		} catch (ActivityNotFoundException e) {
//			Assert.fail();
//		} catch (InvalidInformationException e) {
//			Assert.fail();
//		} catch (NotLoggedInException e) {
//			Assert.fail();
//		}
//
//		try {
//			employee.registerTime("Derp", null, "did stuff", 3);
//			Assert.fail();
//		} catch (ProjectNotFoundException e) {
//			Assert.fail();
//		} catch (ActivityNotFoundException e) {
//		} catch (InvalidInformationException e) {
//			Assert.fail();
//		} catch (NotLoggedInException e) {
//			Assert.fail();
//		}
//
//		try {
//			employee.registerTime("Derp", "add fish", null, 3);
//		} catch (ProjectNotFoundException e) {
//			Assert.fail();
//		} catch (ActivityNotFoundException e) {
//			Assert.fail();
//		} catch (InvalidInformationException e) {
//			Assert.fail();
//		} catch (NotLoggedInException e) {
//			Assert.fail();
//		}
//
//		try {
//			employee.registerTime("Derp", "add fish", "did stuff", -23423);
//			Assert.fail();
//		} catch (ProjectNotFoundException e) {
//			Assert.fail();
//		} catch (ActivityNotFoundException e) {
//			Assert.fail();
//		} catch (InvalidInformationException e) {
//			assertEquals(e.getMessage(), "Used time can't be less than 0");
//		} catch (NotLoggedInException e) {
//			Assert.fail();
//		}
//	}
}
