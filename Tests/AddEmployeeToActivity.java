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
import org.junit.Test;

import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.MissingProjectException;

public class AddEmployeeToActivity {
	
	@Test
	public void AddEmployeeToActivitySuccessTest()
	{
		Scheduler scheduler = new Scheduler();
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
		
		String activityName = "Udvikling af brugerinterface";
		String activityDetailedDescription = "oprettelsen af et brugerinterface for programmet";
		int expectedHours = 200;
		Calendar startDate = new GregorianCalendar();
		startDate.set(2016, 3, 16);
		Calendar endDate = new GregorianCalendar();
		endDate.set(2016, 4, 18);
		try {
			scheduler.addEmployee("JBS");
			scheduler.addEmployee("ELL");
			scheduler.addEmployee("AGC");
			scheduler.addEmployee("NR");
		} catch (Exception e) {
			Assert.fail();
		}
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
	}
}
