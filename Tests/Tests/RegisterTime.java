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

public class RegisterTime {
	
	private Scheduler scheduler = null;

	@Before
	public void setup()
	{
		scheduler = new Scheduler();
		TestTools.login(scheduler);
		try {
			TestTools.addEmployee(scheduler, "JSB");
			TestTools.createProject(scheduler, "Derp");
			TestTools.addEmployeeToProject(scheduler, "JSB", "Derp");
			TestTools.addActivity(scheduler, "Derp", "add fish", new String[] { "JSB" });
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void RegisterTimeSuccessTest() throws ProjectNotFoundException, NotLoggedInException, ActivityNotFoundException
	{
		Employee employee = null;
		try {
			employee = scheduler.getEmployeeFromInitials("JSB");
			employee.registerTime("Derp", "add fish", "did stuff", 3);
		} catch (Exception e) {
			Assert.fail();
		}
		RegisteredTime registeredTime = scheduler.getTimeVault().getEmployeeTime("JSB").get(0);
		assertEquals(registeredTime.getMessage(), "did stuff");
		assertTrue(registeredTime.getEmployee() == employee);
		assertEquals(registeredTime.getTime(), 3);
		assertEquals(1, scheduler.getTimeVault().getEmployeeTime("JSB").size());
		assertEquals(1, scheduler.getTimeVault().getProjectTime("Derp").size());
		assertEquals(1, scheduler.getTimeVault().getActivityTime("Derp", "add fish").size());
		assertTrue(registeredTime == scheduler.getTimeVault().getProjectTime("Derp").get(0));
		assertTrue(registeredTime == scheduler.getTimeVault().getActivityTime("Derp", "add fish").get(0));
	}
	
	@Test
	public void RegisterTimeMissingOrInvalidInformationTest()
	{
		Employee employee = null;
		try {
			employee = scheduler.getEmployeeFromInitials("JSB");
		} catch (Exception e) {
			Assert.fail();
		}
		
		try {
			employee.registerTime(null, "add fish", "did stuff", 3);
			Assert.fail();
		} catch (ProjectNotFoundException e) {
		} catch (ActivityNotFoundException e) {
			Assert.fail();
		} catch (InvalidInformationException e) {
			Assert.fail();
		} catch (NotLoggedInException e) {
			Assert.fail();
		}
		
		try {
			employee.registerTime("Derp", null, "did stuff", 3);
			Assert.fail();
		} catch (ProjectNotFoundException e) {
			Assert.fail();
		} catch (ActivityNotFoundException e) {
		} catch (InvalidInformationException e) {
			Assert.fail();
		} catch (NotLoggedInException e) {
			Assert.fail();
		}
		
		try {
			employee.registerTime("Derp", "add fish", null, 3);
		} catch (ProjectNotFoundException e) {
			Assert.fail();
		} catch (ActivityNotFoundException e) {
			Assert.fail();
		} catch (InvalidInformationException e) {
			Assert.fail();
		} catch (NotLoggedInException e) {
			Assert.fail();
		}
		
		try {
			employee.registerTime("Derp", "add fish", "did stuff",-23423);
			Assert.fail();
		} catch (ProjectNotFoundException e) {
			Assert.fail();
		} catch (ActivityNotFoundException e) {
			Assert.fail();
		} catch (InvalidInformationException e) {
			assertEquals(e.getMessage(), "Used time can't be less than 0");
		} catch (NotLoggedInException e) {
			Assert.fail();
		}
	}
}
