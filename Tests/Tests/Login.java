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
import org.junit.Test;

import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;

public class Login {

	@Test
	public void loginSuccessTest()
	{
		Scheduler scheduler = new Scheduler();
		try {
			scheduler.addEmployee("DERP");
		} catch (Exception e) {
			Assert.fail();
		}
		
		assertFalse(scheduler.isAnyoneLoggedIn());
		try {
			scheduler.login("DERP");
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		}
		assertTrue(scheduler.isAnyoneLoggedIn());
		assertEquals(scheduler.getLoggedInEmployee().getInitials(), "DERP");
	}
	
	@Test
	public void loginEmployeeNotFoundTest()
	{
		Scheduler scheduler = new Scheduler();
		
		try {
			scheduler.login("DERP");
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			assertEquals(e.getMessage(), "No employee with those initials exists");
		}
	}
}
