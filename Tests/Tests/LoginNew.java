package Tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.AlreadyLoggedInException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;

/**
 * @author Andreas
 */
public class LoginNew {
Scheduler scheduler;
	
	/**
	 * Andreas
	 */
	@Before
	public void setup(){
		scheduler = new Scheduler();
		try {
			scheduler.addEmployee("JSB");
			scheduler.addEmployee("ELL");
			scheduler.addEmployee("AGC");
			scheduler.addEmployee("NR");
			scheduler.addEmployee("AM");
		} catch (Exception e) {
			Assert.fail();
		}
	}

	/**
	 * Andreas
	 */
	private void addLeLa(){
		try {
			scheduler.addEmployee("LeLa");
		} catch (Exception e) {
			Assert.fail();
		}
		
	}
	
	/**
	 * Andreas
	 */
	private void loginAM() {
		try {
			scheduler.login("AM");
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		}
	}
	
	
	/**
	 * Andreas
	 */
	@Test
	public void loginNotLoggedInExistingUser()
	{
		addLeLa();	
		assertFalse(scheduler.isAnyoneLoggedIn());		
		try {
			scheduler.login("LeLa");
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		}
		assertTrue(scheduler.isAnyoneLoggedIn());
		assertEquals(scheduler.getLoggedInEmployee().getInitials(), "LeLa");
	}
	
	/**
	 * Andreas
	 */
	@Test
	public void loginNotLoggedInNonExistingUser()
	{
		assertFalse(scheduler.isAnyoneLoggedIn());		
		try {
			scheduler.login("LeLa");
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			assertEquals("No employee with those initials exists", e.getMessage());
			assertFalse(scheduler.isAnyoneLoggedIn());
			assertEquals(scheduler.getLoggedInEmployee(), null);
		} 		
	}
	
	/**
	 * Andreas
	 */
	@Test
	public void loginLoggedInExistingUser()
	{
		addLeLa();
		loginAM();
		assertTrue(scheduler.isAnyoneLoggedIn());		
		try {
			scheduler.login("LeLa");
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		}
		assertTrue(scheduler.isAnyoneLoggedIn());
		assertEquals(scheduler.getLoggedInEmployee().getInitials(), "LeLa");
	}
	
	/**
	 * Andreas
	 */
	@Test
	public void loginLoggedInNonExistingUser()
	{		
		loginAM();
		assertTrue(scheduler.isAnyoneLoggedIn());		
		try {
			scheduler.login("LeLa");
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			assertEquals("No employee with those initials exists", e.getMessage());
			assertTrue(scheduler.isAnyoneLoggedIn());
			assertEquals(scheduler.getLoggedInEmployee().getInitials(), "AM");
		} 		
	}	
}
