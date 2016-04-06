import static org.junit.Assert.*;

import org.junit.Test;

import SoftwareHouse.Employee;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;

import org.junit.Assert;

public class AddNewEmployee {

	@Test
	public void addEmployeeSuccessTest()
	{
		Scheduler scheduler = new Scheduler();
		try {
			scheduler.addEmployee("DERP");
		} catch (Exception e) {
			Assert.fail();
		}
		Employee employee = null;
		try {
			employee = scheduler.getEmployeeFromInitials("DERP");
		} catch (Exception e) {
			Assert.fail();
		}
		assertFalse(employee == null);
		assertEquals(employee.getInitials(), "DERP");
	}
	
	@Test
	public void addEmployeeDuplicateInitialsTest()
	{
		Scheduler scheduler = new Scheduler();
		try {
			scheduler.addEmployee("DERP");
		} catch (Exception e) {
			Assert.fail();
		}
		try {
			scheduler.addEmployee("DERP");
			Assert.fail();
		} catch (MissingInformationException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			assertEquals(e.getMessage(), "An employee with those initials already exist");
		} 
	}
	
	@Test
	public void addEmployeeNoNameTest()
	{
		Scheduler scheduler = new Scheduler();
		//need to refractor this into method calls
		try {
			scheduler.addEmployee("");
			Assert.fail();
		} catch (MissingInformationException  e) {
			assertEquals(e.getMessage(), "Missing employee initials");
		} catch (DuplicateNameException e) {
			Assert.fail();
		}
		try {
			scheduler.addEmployee(" ");
			Assert.fail();
		} catch (MissingInformationException  e) {
			assertEquals(e.getMessage(), "Missing employee initials");
		} catch (DuplicateNameException e) {
			Assert.fail();
		}
		try {
			scheduler.addEmployee("     ");
			Assert.fail();
		} catch (MissingInformationException  e) {
			assertEquals(e.getMessage(), "Missing employee initials");
		} catch (DuplicateNameException e) {
			Assert.fail();
		}
		try {
			scheduler.addEmployee(null);
			Assert.fail();
		} catch (MissingInformationException  e) {
			assertEquals(e.getMessage(), "Missing employee initials");
		} catch (DuplicateNameException e) {
			Assert.fail();
		}
	}
}
