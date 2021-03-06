package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import SoftwareHouse.Employee;
import SoftwareHouse.RegisteredTime;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;

public class RegisterTime {

	private Scheduler scheduler = null;

	@Before
	public void setup() {
		scheduler = new Scheduler();
		TestTools.login(scheduler);
		try {
			TestTools.addEmployee(scheduler, "AAAA");
			TestTools.addEmployee(scheduler, "AGC");
			
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
			employee.registerTime("Navision Stat", "Brugerinterface", "Gridbag layout f�rdigt sat op og justeret", 5);
		} catch (Exception e) {
			Assert.fail();
		}
		
		// Test that all local fields are correct
		RegisteredTime registeredTime = scheduler.getTimeVault().getEmployeeTime("AAAA").get(0);
		assertEquals(registeredTime.getMessage(), "Gridbag layout f�rdigt sat op og justeret");
		assertTrue(registeredTime.getEmployee() == employee);
		assertEquals(registeredTime.getTime(), 5);
		assertEquals(1, scheduler.getTimeVault().getEmployeeTime("AAAA").size());
		assertEquals(1, scheduler.getTimeVault().getProjectTime("Navision Stat").size());
		assertEquals(1, scheduler.getTimeVault().getActivityTime("Navision Stat", "Brugerinterface").size());
		assertTrue(registeredTime == scheduler.getTimeVault().getProjectTime("Navision Stat").get(0));
		assertTrue(registeredTime == scheduler.getTimeVault().getActivityTime("Navision Stat", "Brugerinterface").get(0));
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
			employee.registerTime("Navision Stat", "Brugerinterface", "Gridbag layout f�rdigt sat op og justeret", -42);
			Assert.fail();
		} catch (Exception e) {
			assertEquals("Used time can't be less than 0", e.getMessage());
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
