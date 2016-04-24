import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.MissingProjectException;

public class TestRegisterTime {

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
	public void testRegisterTime() {
		Employee employee = null;
		try {
			employee = scheduler.getEmployeeFromInitials("AGC");
		} catch (EmployeeNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
		
		//TODO Finish test
		
		
		
	}
	
}
