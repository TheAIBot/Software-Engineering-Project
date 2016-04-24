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
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.MissingProjectException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;

/**
 * @author ELL
 */
public class TestEditActivity {
	
	Scheduler scheduler;
	
	@Before
	public void setup() {
		scheduler = new Scheduler();
		
		// Create employees
		try {
			scheduler.addEmployee("AGC");
			scheduler.addEmployee("ELL");
		} catch (Exception e) {
			Assert.fail();
		}
		
		// Create project
		try {
			scheduler.createProject("Navision Stat");
		} catch (Exception e) {
			Assert.fail();
		}
		Project project = null;
		try {
			project = scheduler.getProject("Navision Stat");
		} catch (MissingProjectException e1) {
			Assert.fail();
		}
		try {
			project.addEmployee("AGC");
			project.addEmployee("ELL");
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
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
			project.addAcitivity(activityName,	activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testEditActivitySucces() {
		Activity activity = null;
		try {
			activity = scheduler.getActivity("Navision Stat", "Brugerinterface");
		} catch (ProjectNotFoundException e) {
			e.printStackTrace();
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
		
		assertNotNull(activity);
		assertEquals(activity.getBudgettedTime(), 150);
		try {
			activity.setBudgettedTime(275);
		} catch (InvalidInformationException e) {
			e.printStackTrace();
			Assert.fail();
		}
		assertEquals(activity.getBudgettedTime(), 275);
	}
	
	@Test
	public void testEditActivityRemoveInformation() {
		Activity activity = null;
		try {
			activity = scheduler.getActivity("Navision Stat", "Brugerinterface");
		} catch (ProjectNotFoundException e) {
			e.printStackTrace();
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
		
		//Try to remove the title of the activity
		assertNotNull(activity);
		assertEquals(activity.getName(), "Brugerinterface");
		try {
			activity.setName(null);
			Assert.fail();
		} catch (Exception e) {
			assertEquals("Missing title", e.getMessage());
		}
	}
	
	@Test
	public void testEditActivityInvalidInformation() {
		Activity activity = null;
		try {
			activity = scheduler.getActivity("Navision Stat", "Brugerinterface");
		} catch (ProjectNotFoundException e) {
			e.printStackTrace();
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
		
		//Try to change the budgeted time to a negative value 
		assertNotNull(activity);
		assertEquals(activity.getName(), "Brugerinterface");
		try {
			activity.setBudgettedTime(-1);
			Assert.fail();
		} catch (Exception e) {
			assertEquals("Budgetted time can't be less than 0", e.getMessage());
		}
	}
	
	
}
