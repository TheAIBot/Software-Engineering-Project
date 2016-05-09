package Tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import SoftwareHouse.Activity;
import SoftwareHouse.Project;
import SoftwareHouse.RegisteredTime;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeAlreadyAssignedException;
import SoftwareHouse.ExceptionTypes.EmployeeMaxActivitiesReachedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.IllegalCharException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectManagerNotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;
import SoftwareHouse.ExceptionTypes.TooManyCharsException;

/**
 * @author ELL
 */
public class EditActivity {

	Scheduler scheduler;

	/**
	 * Emil
	 */
	@Before
	public void setup() {
		scheduler = new Scheduler();
		
		// Test cannot fetch activities or projects without being logged in
		assertFalse(scheduler.isAnyoneLoggedIn());
		try {
			scheduler.getActivity("sygdom", "sygdom");
			Assert.fail();
		} catch (Exception e) {
		}
		try {
			scheduler.getProjectsContainingStringInName("sy");
			Assert.fail();
		} catch (Exception e) {
		}
		
		// Login
		TestTools.login(scheduler);
		assertTrue(scheduler.isAnyoneLoggedIn());
		
		// Create employees
		try {
			scheduler.addEmployee("AGC");
			scheduler.addEmployee("ELL");
		} catch (Exception e) {
			Assert.fail();
		}
		// Create project
		try {
			TestTools.createProject(scheduler,"Navision Stat");
		} catch (Exception e) {
			Assert.fail();
		}
		Project project = null;
		try {
			project = scheduler.getProject("Navision Stat");
		} catch (Exception e) {
			Assert.fail();
		}
		try {
			if(!(project.addEmployee("ELL") &&	project.addEmployee("AGC"))){
				Assert.fail();
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

		// Create activity
		String activityName = "Brugerinterface";
		String activityDetailedDescription = "Udvikling af brugerinterface";
		int expectedHours = 150;
		Calendar startDate = new GregorianCalendar();
		startDate.set(2016, 3, 16);
		Calendar endDate = new GregorianCalendar();
		endDate.set(2016, 4, 18);
		List<String> employeeInitials = new ArrayList<String>();
		employeeInitials.add("AGC");
		employeeInitials.add("ELL");
		try {
			project.addAcitivity(activityName, activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	/**
	 * Emil
	 * Change name of acitivity "Brugerinterface" to "Test"
	 */
	@Test
	public void testEditActivityNameSucces() {
		Activity activity = null;
		try {
			activity = scheduler.getActivity("Navision Stat", "Brugerinterface");
		} catch (Exception e) {
			Assert.fail();
		}

		assertNotNull(activity);
		assertEquals(activity.getName(), "Brugerinterface");
		try {
			activity.setName("Test");
		} catch (Exception e) {
			Assert.fail();
		}
		assertEquals(activity.getName(), "Test");
	}

	/**
	 * Emil
	 * Try to remove the title of the activity
	 */
	@Test
	public void testEditActivityRemoveName() {
		Activity activity = null;
		try {
			activity = scheduler.getActivity("Navision Stat", "Brugerinterface");
		} catch (Exception e) {
			Assert.fail();
		}

		assertNotNull(activity);
		assertEquals(activity.getName(), "Brugerinterface");
		try {
			activity.setName("");
			Assert.fail();
		} catch (Exception e) {
			assertEquals("Missing name", e.getMessage());
		}
	}
	
	/**
	 * Emil
	 * Try to change the budgeted time to a negative value
	 */
	@Test
	public void testEditActivityInvalidBudegtedTime() {
		Activity activity = null;
		try {
			activity = scheduler.getActivity("Navision Stat", "Brugerinterface");
		} catch (Exception e) {
			Assert.fail();
		}

		assertNotNull(activity);
		assertEquals(activity.getName(), "Brugerinterface");
		assertEquals(150, activity.getBudgettedTime());
		try {
			activity.setBudgettedTime(-1);
			Assert.fail();
		} catch (Exception e) {
			assertEquals("Budgetted time can't be less than 0", e.getMessage());
		}
	}

	/**
	 * Emil
	 * Try to remove the detailed text of the activity
	 * @throws ProjectManagerNotLoggedInException 
	 */
	@Test
	public void testEditActivityRemoveDetailedText() throws ProjectManagerNotLoggedInException {
		Activity activity = null;
		try {
			activity = scheduler.getActivity("Navision Stat", "Brugerinterface");
		} catch (Exception e) {
			Assert.fail();
		}

		assertNotNull(activity);
		assertEquals(activity.getName(), "Brugerinterface");
		try {
			activity.setDetailText("");
			assertEquals("", activity.getDetailText());
		} catch (MissingInformationException e) {
			Assert.fail();
		}
	}
	
	/**
	 * Emil
	 * Change name of acitivity "Brugerinterface" to "Rendering", which is already taken 
	 */
	@Test
	public void testEditActivityNameFailure() {
		// Create activity "Rendering"
		Project project = null;
		try {
			project = scheduler.getProject("Navision Stat");
		} catch (Exception e) {
			Assert.fail();
		}
		String activityName = "Rendering";
		String activityDetailedDescription = "Udvikling af renderingsystem";
		int expectedHours = 125;
		Calendar startDate = new GregorianCalendar();
		startDate.set(2016, 3, 16);
		Calendar endDate = new GregorianCalendar();
		endDate.set(2016, 4, 18);
		List<String> employeeInitials = new ArrayList<String>();
		employeeInitials.add("AGC");
		employeeInitials.add("ELL");
		try {
			project.addAcitivity(activityName, activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
		} catch (Exception e) {
			Assert.fail();
		}
		Activity activityRendering = null;
		try {
			activityRendering = scheduler.getActivity("Navision Stat", "Rendering");
		} catch (Exception e) {
			Assert.fail();
		}
		assertNotNull(activityRendering);
		assertEquals(activityRendering.getName(), "Rendering");
		
		Activity activityBrugerinterface = null;
		try {
			activityBrugerinterface = scheduler.getActivity("Navision Stat", "Brugerinterface");
		} catch (Exception e) {
			Assert.fail();
		}
		assertNotNull(activityBrugerinterface);
		assertEquals(activityBrugerinterface.getName(), "Brugerinterface");
		
		try {
			activityBrugerinterface.setName("Rendering");
			Assert.fail();
		} catch (Exception e) {
			assertEquals("An activity with the specified name already exists", e.getMessage());
			assertEquals("Brugerinterface", activityBrugerinterface.getName());
		}
	}
	
	/**
	 * Emil
	 */
	@Test
	public void testNoActivityTime()
	{
		try {
			TestTools.addEmployee(scheduler, "bob");
		} catch (Exception e){
			Assert.fail();
		}
		String[] employees = new String[1];
		employees[0] = "bob";
		
		TestTools.addEmployeeToProject(scheduler, "bob", "Navision Stat");
		
		try {
			TestTools.addActivity(scheduler, "Navision Stat", "abde", employees);
		} catch (Exception e){
			Assert.fail();
		}
		
		try {
			List<RegisteredTime> list = scheduler.getTimeVault().getActivityTime("Navision Stat", "abde");
			assertEquals(list.size(),0);
		} catch (Exception e) {
			Assert.fail();
		}
		
	}
	
	/**
	 * Emil
	 */
	@Test
	public void testGetNonExistingActivityOrProject()
	{
		try {
			scheduler.getActivity("Navision Stat", "xxxx");
			Assert.fail();
		} catch (Exception e) {
		}
		
		try {
			scheduler.getActivity("xxxx", "Brugerinterface");
			Assert.fail();
		} catch (Exception e) {
		}
		
	}
	
	


}
