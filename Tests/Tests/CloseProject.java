package Tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectAlreadyClosedException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;

public class CloseProject {
	private Scheduler scheduler;
	
	@Before
	public void setup()
	{
		scheduler = new Scheduler();
		
		TestTools.login(scheduler);
		try {
			TestTools.createProject(scheduler,"Navision Stat");
		} catch (Exception e1) {
			Assert.fail();
		}
		addActivity("Add more doom");
		addActivity("Add more fish");
		addActivity("Add more cake");
	}
	
	
	@Test
	public void CloseProjectSuccessTest()
	{
		Project project = null;
		try {
			assertEquals(1,scheduler.getProjectsContainingStringInName("Navision Stat").size());
			project = scheduler.getProjectsContainingStringInName("Navision Stat").get(0);
		} catch (Exception e) {
			Assert.fail();
		}
		assertTrue(project.isOpen());
		assertEquals(project.getOpenActivities().size(), 3);
		try {
			project.close();
		} catch (ProjectAlreadyClosedException e) {
			Assert.fail();
		}
		assertFalse(project.isOpen());
		assertEquals(project.getOpenActivities().size(), 0);
		assertEquals(project.getClosedActivities().size(), 3);
		
	}
	
	@Test
	public void CloseProjectAlreadyClosed()
	{
		Project project = null;
		try {
			project = scheduler.getProject("Navision Stat");
		} catch (Exception e) {
			Assert.fail();
		}
		
		try {
			project.close();
		} catch (ProjectAlreadyClosedException e) {
			Assert.fail();
		}
		
		try {
			project.close();
			Assert.fail();
		} catch (ProjectAlreadyClosedException e) {
			assertEquals(e.getMessage(), "The project \"Navision Stat\" is already closed");
		}
		assertFalse(project.isOpen());
		assertEquals(project.getOpenActivities().size(), 0);
		assertEquals(project.getClosedActivities().size(), 3);
	}
	
	private void addActivity(String activityName)
	{
		Project project = null;
		try {
			project = scheduler.getProject("Navision Stat");
		} catch (Exception e) {
			Assert.fail();
		}
		String activityDetailedDescription = "oprettelsen af et brugerinterface for programmet";
		int expectedHours = 200;
		Calendar startDate = new GregorianCalendar();
		startDate.set(2016, 3, 16);
		Calendar endDate = new GregorianCalendar();
		endDate.set(2016, 4, 18);
		List<String> employeeInitials = new ArrayList<String>();
		
		try {
			project.forceAddAcitivity(activityName,	activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
		} catch (Exception e) {
			Assert.fail();
		}
	}
}
