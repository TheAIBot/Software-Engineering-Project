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

//Test-kommentar
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
	
	private void loginAM(){ //TODO (*) Fails are not registrated outside the method.
		try {
			scheduler.login("AM");
		} catch(AlreadyLoggedInException e){
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		}
	}
	
	
	@Test
	public void loginNotLoggedInExistingUser()
	{
		addLeLa();	
		assertFalse(scheduler.isAnyoneLoggedIn());		
		try {
			scheduler.login("LeLa");
		} catch(AlreadyLoggedInException e){
			Assert.fail();
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
		try {
			scheduler.login("LeLa");
			Assert.fail();
		} catch(AlreadyLoggedInException e){
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			assertEquals("No employee with those initials exists", e.getMessage());
			assertFalse(scheduler.isAnyoneLoggedIn());
			assertEquals(scheduler.getLoggedInEmployee(), null);
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
		} catch(AlreadyLoggedInException e){
			Assert.fail();
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
		} catch(AlreadyLoggedInException e){
			Assert.fail();
		}catch (EmployeeNotFoundException e) {
			assertEquals("No employee with those initials exists", e.getMessage());
			assertFalse(scheduler.isAnyoneLoggedIn());
			assertEquals(scheduler.getLoggedInEmployee().getInitials(), "AM");
		} 		
	}
	
	@Test
	public void loginLoggedInSameUser()
	{		
		try {
			scheduler.login("LeLa");
		} catch (Exception e) {
			Assert.fail();
		}
		assertTrue(scheduler.isAnyoneLoggedIn());
		assertEquals(scheduler.getLoggedInEmployee().getInitials(), "LeLa");
		try {
			scheduler.login("LeLa");
			Assert.fail();
		} catch(AlreadyLoggedInException e) {
			assertEquals("LeLa is already logged in", e.getMessage());
			assertTrue(scheduler.isAnyoneLoggedIn());
			assertEquals(scheduler.getLoggedInEmployee().getInitials(), "LeLa");			
		}		catch (Exception e) {
			Assert.fail();
		} 		
	}
	
	
	
}
