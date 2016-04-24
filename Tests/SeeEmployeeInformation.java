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

import SoftwareHouse.Activity;
import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;

public class SeeEmployeeInformation {
	private Scheduler scheduler = null;
	
	@Before
	public void setup()
	{
		scheduler = new Scheduler();
		try {
			scheduler.createProject("15-puzzle-spil");
		} catch (Exception e) {
			Assert.fail();
		}
		
		Project project = null;
		try {
			project = scheduler.getProject("15-puzzle-spil");
		} catch (Exception e) {
			Assert.fail();
		}
		assertEquals(project.getOpenActivities().size(), 0);		
		try {
			scheduler.addEmployee("JBS");
			scheduler.addEmployee("ELL");
			scheduler.addEmployee("AGC");
			scheduler.addEmployee("NR");
			scheduler.addEmployee("AM");
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void SeeEmployeeInformationSuccesfullTest() {
		List<Employee> employeesFound = scheduler.getEmployeesContaininingString("AM");
		Employee hopefullyArndt = scheduler.getEmployeeFromInitials("AM");
		List<Project> employeesProjects =  hopefullyArndt.getProjects();
		assertTrue(employeesProjects.size() = 1);
		assertTrue()
		List<Activity> employeesActivities = hopefullyArndt.getActivities();
		String employeeDescription = hopefullyArndt.getDescription();
		History employeesHistory = hopefullyArndt.get
		
		
	}

}
