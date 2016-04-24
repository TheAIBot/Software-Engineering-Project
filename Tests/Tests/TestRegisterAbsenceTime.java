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

public class TestRegisterAbsenceTime {

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
}
