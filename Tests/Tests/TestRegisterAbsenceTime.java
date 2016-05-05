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

/**
 * @author ELL
 */
public class TestRegisterAbsenceTime {

	private Scheduler scheduler;
	private String apn;

	@Before
	public void setup() {
		scheduler = new Scheduler();
		TestTools.login(scheduler);
		apn = scheduler.getAbsenceProject().getName();
		try {
			TestTools.addEmployee(scheduler, "BM");
			TestTools.addEmployeeToProject(scheduler, "BM", apn);
			TestTools.addActivity(scheduler, apn, "Holiday", new String[] { "BM" });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void RegisterTimeSuccessTest()
			throws ProjectNotFoundException, NotLoggedInException, ActivityNotFoundException {
		Employee employee = null;
		try {
			employee = scheduler.getEmployeeFromInitials("BM");
			employee.registerTime(apn, "Holiday", "did stuff", 3);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		RegisteredTime registeredTime = scheduler.getTimeVault().getEmployeeTime("BM").get(0);
		assertEquals(registeredTime.getMessage(), "did stuff");
		assertTrue(registeredTime.getEmployee() == employee);
		assertEquals(registeredTime.getTime(), 3);
		assertEquals(1, scheduler.getTimeVault().getEmployeeTime("BM").size());
		assertEquals(1, scheduler.getTimeVault().getProjectTime(apn).size());
		assertEquals(1, scheduler.getTimeVault().getActivityTime(apn, "Holiday").size());
		assertTrue(registeredTime == scheduler.getTimeVault().getProjectTime(apn).get(0));
		assertTrue(registeredTime == scheduler.getTimeVault().getActivityTime(apn, "Holiday").get(0));
	}
}
