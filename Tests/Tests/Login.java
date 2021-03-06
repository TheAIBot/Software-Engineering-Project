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

public class Login {
	Scheduler scheduler;
	
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
	
	private void addLeLa(){
		try {
			scheduler.addEmployee("LeLa");
		} catch (Exception e) {
			Assert.fail();
		}	
	}
	
	private void loginAM(){ 
		// Verify that cannot retrieve projects without being logged in
		try {
			scheduler.getProject("sygdom");
			Assert.fail();
		} catch (Exception e) {
		}
		try {
			scheduler.getProjects();
			Assert.fail();
		} catch (Exception e) {
		}
		
		try {
			scheduler.login("AM");
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		}
	}
	
	
	@Test
	public void loginNotLoggedInExistingUser()
	{
		addLeLa();	
		assertFalse(scheduler.isAnyoneLoggedIn());	
		assertEquals(null, scheduler.getLoggedInEmployee());
		try {
			scheduler.login("LeLa");
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		}
		assertTrue(scheduler.isAnyoneLoggedIn());
		assertEquals(scheduler.getLoggedInEmployee().getInitials(), "LeLa");
	}
	
	@Test
	public void loginNotLoggedInNonExistingUser()
	{
		assertFalse(scheduler.isAnyoneLoggedIn());	
		assertEquals(null, scheduler.getLoggedInEmployee());
		try {
			scheduler.login("LeLa");
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			assertEquals("No employee with those initials exists", e.getMessage());
			assertFalse(scheduler.isAnyoneLoggedIn());
			assertEquals(null, scheduler.getLoggedInEmployee());
		} 		
	}
	
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
