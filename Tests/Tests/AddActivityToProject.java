package Tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeAlreadyAssignedException;
import SoftwareHouse.ExceptionTypes.EmployeeMaxActivitiesReachedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectManagerNotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;

public class AddActivityToProject {
	
	private Scheduler scheduler = null;
	
	/**
	 * Setup the test environment by initializing the scheduler, login, creating a sample project and staff it with employees 
	 */
	@Before
	public void setup()
	{
		scheduler = new Scheduler();
		TestTools.login(scheduler);
		Project project = null;
		try {
			project = TestTools.createProject(scheduler, "Navision Stat");
		} catch (Exception e) {
			Assert.fail();
		}
		assertEquals(project.getOpenActivities().size(), 0);
		
		TestTools.addEmployeeToProject(scheduler, "JBS", "Navision Stat");
		TestTools.addEmployeeToProject(scheduler, "ELL", "Navision Stat");
		TestTools.addEmployeeToProject(scheduler, "AGC", "Navision Stat");
		TestTools.addEmployeeToProject(scheduler, "NR", "Navision Stat");
	}
	
	@Test
	public void AddActivitySuccessTest() {	
		try {
			String projectName = "Navision Stat";
			String activityTitle = "Udvikling af brugerinterface";
			String activityDescription = "Oprettelse af brugerinterface for programmet";
			int numHours = 200;
			Calendar startDate = new GregorianCalendar();
			startDate.set(2016, 3, 16);
			Calendar endDate = new GregorianCalendar();
			endDate.set(2016,4,18);
			String[] employees = {"JBS", "ELL", "AGC", "NR"};
			
			TestTools.addActivity(scheduler, projectName, activityTitle, activityDescription, numHours, startDate, endDate, employees);
		} 
		catch (Exception e) {
			Assert.fail();
		}		
	}
	
	@Test
	public void AddActivityMissingInformationTest()
	{
		Project project = null;
		try {
			project = scheduler.getProject("Navision Stat");
		} catch (Exception e) {
			Assert.fail();
		}
		
		String activityName = "Udvikling af brugerinterface";
		String activityDetailedDescription = "oprettelsen af et brugerinterface for programmet";
		int expectedHours = 200;
		Calendar startDate = new GregorianCalendar();
		startDate.set(2016, 3, 16);
		Calendar endDate = new GregorianCalendar();
		endDate.set(2016, 4, 18);
		List<String> employeeInitials = new ArrayList<String>();
		employeeInitials.add("JBS");
		employeeInitials.add("ELL");
		employeeInitials.add("AGC");
		employeeInitials.add("NR");
		
		try {
			project.addAcitivity(null,	activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
			Assert.fail();
		} catch (MissingInformationException e) {
			assertEquals(e.getMessage(), "Missing title");
		} catch (InvalidInformationException e) {
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (EmployeeMaxActivitiesReachedException e) {
			Assert.fail();
		} catch (ProjectManagerNotLoggedInException e) {
			Assert.fail();
		}
		
		try {
			project.addAcitivity(activityName,	null, employeeInitials, startDate, endDate, expectedHours);
			Assert.fail();
		} catch (MissingInformationException e) {
			assertEquals(e.getMessage(), "Missing detailText");
		} catch (InvalidInformationException e) {
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (EmployeeMaxActivitiesReachedException e) {
			Assert.fail();
		} catch (ProjectManagerNotLoggedInException e) {
			Assert.fail();
		}
		
		try {
			project.addAcitivity(activityName,	activityDetailedDescription, null, startDate, endDate, expectedHours);
			Assert.fail();
		} catch (MissingInformationException e) {
			assertEquals(e.getMessage(), "Missing employees");
		} catch (InvalidInformationException e) {
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (EmployeeMaxActivitiesReachedException e) {
			Assert.fail();
		} catch (ProjectManagerNotLoggedInException e) {
			Assert.fail();
		}
		
		try {
			project.addAcitivity(activityName,	activityDetailedDescription, employeeInitials, null, endDate, expectedHours);
			Assert.fail();
		} catch (MissingInformationException e) {
			assertEquals(e.getMessage(), "Missing start date");
		} catch (InvalidInformationException e) {
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (EmployeeMaxActivitiesReachedException e) {
			Assert.fail();
		} catch (ProjectManagerNotLoggedInException e) {
			Assert.fail();
		}
		
		try {
			project.addAcitivity(activityName,	activityDetailedDescription, employeeInitials, startDate, null, expectedHours);
			Assert.fail();
		} catch (MissingInformationException e) {
			assertEquals(e.getMessage(), "Missing end date");
		} catch (InvalidInformationException e) {
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (EmployeeMaxActivitiesReachedException e) {
			Assert.fail();
		} catch (ProjectManagerNotLoggedInException e) {
			Assert.fail();
		}
		
		try {
			project.addAcitivity(activityName,	activityDetailedDescription, employeeInitials, endDate, startDate, expectedHours);
			Assert.fail();
		} catch (MissingInformationException e) {
			Assert.fail();
		} catch (InvalidInformationException e) {
			assertEquals(e.getMessage(), "End date has to start after start date");
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (EmployeeMaxActivitiesReachedException e) {
			Assert.fail();
		} catch (ProjectManagerNotLoggedInException e) {
			Assert.fail();
		}
		
		try {
			project.addAcitivity(activityName,	activityDetailedDescription, employeeInitials, startDate, endDate, -1);
			Assert.fail();
		} catch (MissingInformationException e) {
			Assert.fail();
		} catch (InvalidInformationException e) {
			assertEquals(e.getMessage(), "Budgetted time can't be less than 0");
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (EmployeeMaxActivitiesReachedException e) {
			Assert.fail();
		} catch (ProjectManagerNotLoggedInException e) {
			Assert.fail();
		}
		
	}
	
	@Test
	public void AddActivityEmployeeNotFoundTest()
	{		
		try {
			TestTools.addActivity(scheduler, "Navision Stat", "Udvikling af brugerinterface", new String[] {"JBS", "ELL", "AGC", "BOB"});
			Assert.fail();
		} catch(EmployeeNotFoundException e) {
			assertEquals(e.getMessage(), "Employee with initials: BOB does not exist or is not part of this project");
		} catch (MissingInformationException e) {
			Assert.fail();
		} catch (InvalidInformationException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (ProjectNotFoundException e) {
			Assert.fail();
		} catch (ActivityNotFoundException e) {
			Assert.fail();
		} catch (NotLoggedInException e) {
			Assert.fail();
		} catch (EmployeeMaxActivitiesReachedException e) {
			Assert.fail();
		} catch (ProjectManagerNotLoggedInException e) {
			Assert.fail();
		}
		
		try {
			TestTools.forceAddActivity(scheduler, "Navision Stat", "Udvikling af brugerinterface", new String[] {"JBS", "ELL", "AGC", "BOB"});
			Assert.fail();
		} catch(EmployeeNotFoundException e) {
			assertEquals(e.getMessage(), "Employee with initials: BOB does not exist or is not part of this project");
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (ProjectNotFoundException e) {
			Assert.fail();
		} catch (ActivityNotFoundException e) {
			Assert.fail();
		} catch (NotLoggedInException e) {
			Assert.fail();
		} catch (EmployeeMaxActivitiesReachedException e) {
			Assert.fail();
		} catch (ProjectManagerNotLoggedInException e) {
			Assert.fail();
		}
		
		try {
			scheduler.addEmployee("DERP");
		} catch (Exception e) {
			Assert.fail();
		}
		
		try {
			TestTools.addActivity(scheduler, "Navision Stat", "Udvikling af brugerinterface", new String[] {"JBS", "DERP"});
			Assert.fail();
		} catch(EmployeeNotFoundException e) {
			assertEquals(e.getMessage(), "Employee with initials: DERP does not exist or is not part of this project");
		} catch (MissingInformationException e) {
			Assert.fail();
		} catch (InvalidInformationException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (ProjectNotFoundException e) {
			Assert.fail();
		} catch (ActivityNotFoundException e) {
			Assert.fail();
		} catch (NotLoggedInException e) {
			Assert.fail();
		} catch (EmployeeMaxActivitiesReachedException e) {
			Assert.fail();
		} catch (ProjectManagerNotLoggedInException e) {
			Assert.fail();
		}
		
		try {
			TestTools.forceAddActivity(scheduler, "Navision Stat", "Udvikling af brugerinterface", new String[] {"JBS", "DERP"});
			Assert.fail();
		} catch(EmployeeNotFoundException e) {
			assertEquals(e.getMessage(), "Employee with initials: DERP does not exist or is not part of this project");
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (ProjectNotFoundException e) {
			Assert.fail();
		} catch (ActivityNotFoundException e) {
			Assert.fail();
		} catch (NotLoggedInException e) {
			Assert.fail();
		} catch (EmployeeMaxActivitiesReachedException e) {
			Assert.fail();
		} catch (ProjectManagerNotLoggedInException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void AddActivityForceAddNoEmployeesTest()
	{
		Project project = null;
		try {
			project = scheduler.getProject("Navision Stat");
		} catch (Exception e) {
			Assert.fail();
		}
		
		String activityName = "Udvikling af brugerinterface";
		String activityDetailedDescription = "oprettelsen af et brugerinterface for programmet";
		int expectedHours = 200;
		Calendar startDate = new GregorianCalendar();
		startDate.set(2016, 3, 16);
		Calendar endDate = new GregorianCalendar();
		endDate.set(2016, 4, 18);
		
		try {
			project.forceAddAcitivity(activityName,	activityDetailedDescription, null, startDate, endDate, expectedHours);
			Assert.fail();
		} catch (NullPointerException e) {
			
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (EmployeeMaxActivitiesReachedException e) {
			Assert.fail();
		} catch (ProjectManagerNotLoggedInException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void AddActivityDuplicateNameTest()
	{
		Project project = null;
		try {
			project = scheduler.getProject("Navision Stat");
		} catch (Exception e) {
			Assert.fail();
		}
		
		String activityName = "Udvikling af brugerinterface";
		String activityDetailedDescription = "oprettelsen af et brugerinterface for programmet";
		int expectedHours = 200;
		Calendar startDate = new GregorianCalendar();
		startDate.set(2016, 3, 16);
		Calendar endDate = new GregorianCalendar();
		endDate.set(2016, 4, 18);
		List<String> employeeInitials = new ArrayList<String>();
		employeeInitials.add("JBS");
		employeeInitials.add("ELL");
		employeeInitials.add("AGC");
		employeeInitials.add("NR");
		
		try {
			TestTools.addActivity(scheduler, "Navision Stat", "Udvikling af brugerinterface", new String[] {"JBS", "ELL", "AGC", "NR"});
		} catch (Exception e) {
			Assert.fail();
		}
		
		try {
			TestTools.addActivity(scheduler, "Navision Stat", "Udvikling af brugerinterface", new String[] {"JBS", "ELL", "AGC", "NR"});
			Assert.fail();
		} catch (MissingInformationException e) {
			Assert.fail();
		} catch (InvalidInformationException e) {
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			assertEquals(e.getMessage(), "An activity with that name already exists");
		} catch (ProjectNotFoundException e) {
			Assert.fail();
		} catch (ActivityNotFoundException e) {
			Assert.fail();
		} catch (NotLoggedInException e) {
			Assert.fail();
		} catch (EmployeeMaxActivitiesReachedException e) {
			Assert.fail();
		} catch (ProjectManagerNotLoggedInException e) {
			Assert.fail();
		}
		
		try {
			TestTools.forceAddActivity(scheduler, "Navision Stat", "Udvikling af brugerinterface", new String[] {"JBS", "ELL", "AGC", "NR"});
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			assertEquals(e.getMessage(), "An activity with that name already exists");
		} catch (ProjectNotFoundException e) {
			Assert.fail();
		} catch (ActivityNotFoundException e) {
			Assert.fail();
		} catch (NotLoggedInException e) {
			Assert.fail();
		} catch (EmployeeMaxActivitiesReachedException e) {
			Assert.fail();
		} catch (ProjectManagerNotLoggedInException e) {
			Assert.fail();
		}
	}
	
	/**
	 * Test case: Employee does not exists in the internal system
	 * @throws EmployeeAlreadyAssignedException 
	 * @throws EmployeeNotFoundException 
	 */
	@Test
	public void testAddEmployeeNotExisting() throws EmployeeNotFoundException, EmployeeAlreadyAssignedException {
		Project project = null;
		try {
			project = scheduler.getProject("Navision Stat");
		} catch (Exception e) {
			Assert.fail();
		}
		int numberOfEmployeesBefore = project.getEmployees().size();
		
		Employee employeeX = null;
		try {
			employeeX = scheduler.getEmployeeFromInitials("XXXX");
			Assert.fail();
		} catch (Exception e) {
			assertEquals("No employee with those initials exists", e.getMessage());
		}
		
		assertFalse(project.addEmployee("XXXX"));
		assertEquals(numberOfEmployeesBefore, project.getEmployees().size());

	}
}
