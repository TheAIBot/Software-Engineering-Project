import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.Test;

import SoftwareHouse.Activity;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;

import org.junit.Assert;
import org.junit.Before;

public class AddActivityToProject {
	
	private Scheduler scheduler = null;
	
	@Before
	public void setup()
	{
		scheduler = new Scheduler();
		try {
			scheduler.createProject("Navision Stat");
		} catch (Exception e) {
			Assert.fail();
		}
		
		Project project = null;
		try {
			project = scheduler.getProject("Navision Stat");
		} catch (Exception e) {
			Assert.fail();
		}
		assertEquals(project.getOpenActivities().size(), 0);
		
		try {
			scheduler.addEmployee("JBS");
			scheduler.addEmployee("ELL");
			scheduler.addEmployee("AGC");
			scheduler.addEmployee("NR");
		} catch (Exception e) {
			Assert.fail();
		}
		
		try {
			project.addEmployee("JBS");
			project.addEmployee("ELL");
			project.addEmployee("AGC");
			project.addEmployee("NR");
		} catch (EmployeeNotFoundException e1) {
			Assert.fail();
		}
	}
	
	@Test
	public void AddActivitySuccessTest() {	
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
			project.addAcitivity(activityName,	activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
		} catch (Exception e) {
			Assert.fail();
		}
		assertTrue(project.getOpenActivities().size() == 1);
		
		Activity activity = project.getOpenActivities().get(0);
		assertEquals(activity.getName(), activityName);
		assertEquals(activity.getDetailText(), activityDetailedDescription);
		assertEquals(activity.getBudgettedTime(), expectedHours);
		assertEquals(activity.getTimePeriod().getStartDate(), startDate);
		assertEquals(activity.getTimePeriod().getEndDate(), endDate);		
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
		}	
	}
	
	@Test
	public void AddActivityEmployeeNotFoundTest()
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
		employeeInitials.add("BOB");
		
		try {
			project.addAcitivity(activityName,	activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
			Assert.fail();
		} catch(EmployeeNotFoundException e) {
			assertEquals(e.getMessage(), "Employee with initials: BOB does not exist or is not part of this project");
		} catch (MissingInformationException e) {
			Assert.fail();
		} catch (InvalidInformationException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			Assert.fail();
		}
		
		try {
			project.forceAddAcitivity(activityName,	activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
			Assert.fail();
		} catch(EmployeeNotFoundException e) {
			assertEquals(e.getMessage(), "Employee with initials: BOB does not exist or is not part of this project");
		} catch (DuplicateNameException e) {
			Assert.fail();
		}
		
		try {
			scheduler.addEmployee("DERP");
		} catch (Exception e) {
			Assert.fail();
		}
		
		employeeInitials = new ArrayList<String>();
		employeeInitials.add("JBS");
		employeeInitials.add("DERP");
		
		try {
			project.addAcitivity(activityName,	activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
			Assert.fail();
		} catch(EmployeeNotFoundException e) {
			assertEquals(e.getMessage(), "Employee with initials: DERP does not exist or is not part of this project");
		} catch (MissingInformationException e) {
			Assert.fail();
		} catch (InvalidInformationException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			Assert.fail();
		}
		
		try {
			project.forceAddAcitivity(activityName,	activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
			Assert.fail();
		} catch(EmployeeNotFoundException e) {
			assertEquals(e.getMessage(), "Employee with initials: DERP does not exist or is not part of this project");
		} catch (DuplicateNameException e) {
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
			project.addAcitivity(activityName,	activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
		} catch (Exception e) {
			Assert.fail();
		}
		
		try {
			project.addAcitivity(activityName,	activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
			Assert.fail();
		} catch (MissingInformationException e) {
			Assert.fail();
		} catch (InvalidInformationException e) {
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			assertEquals(e.getMessage(), "An activity with that name already exists");
		}
		
		try {
			project.forceAddAcitivity(activityName,	activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			assertEquals(e.getMessage(), "An activity with that name already exists");
		}
	}
}
