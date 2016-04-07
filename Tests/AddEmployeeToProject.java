import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.Assert;
import org.junit.Test;

import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.MissingProjectException;

public class AddEmployeeToProject {

	@Test
	public void AddEmployeeSuccessTest()
	{
		Scheduler scheduler = new Scheduler();
		try {
			scheduler.createProject("Derp");
		} catch (Exception e) {
			Assert.fail();
		}
		Project project = null;
		try {
			project = scheduler.getProject("Derp");
		} catch (Exception e) {
			Assert.fail();
		}
		
		try {
			scheduler.addEmployee("LSB");
		} catch (Exception e) {
			Assert.fail();
		}
		try {
			project.addEmployee("LSB");
		} catch (Exception e) {
			Assert.fail();
		}
		assertEquals(project.getEmployees().size(), 1);
		
		Employee employee = null;
		try {
			employee = scheduler.getEmployeeFromInitials("LSB");
		} catch (Exception e) {
			Assert.fail();
		}
		
		assertEquals(employee.getProjects().size(), 1);
		assertEquals(employee.getProjects().get(0).getName(), "Derp");
	}
	
	@Test
	public void AddEmployeeIncorrectInitialsTest()
	{
		Scheduler scheduler = new Scheduler();
		try {
			scheduler.createProject("Derp");
		} catch (Exception e) {
			Assert.fail();
		}
		Project project = null;
		try {
			project = scheduler.getProject("Derp");
		} catch (Exception e) {
			Assert.fail();
		}

		try {
			project.addEmployee("LSB");
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			assertEquals(e.getMessage(), "No employee with those initials exists");
		}
	}
}
