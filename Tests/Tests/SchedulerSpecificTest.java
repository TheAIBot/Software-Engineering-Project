package Tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;

/**
 * @author Niklas Refsgaard
 *
 */
public class SchedulerSpecificTest {
	
	Scheduler scheduler = null;
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
	
	/**
	 * @author Niklas Refsgaard
	 */
	@Test
	public void testGetProjectNotFound()
	{
		try {
			scheduler.getProject("no project");
			Assert.fail();
		} catch (ProjectNotFoundException e) {
			assertEquals("Project was not found",e.getMessage());
		} catch (NotLoggedInException e) {
			Assert.fail();
		}
	}

}
