package Tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import SoftwareHouse.Activity;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.Tools;
import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeMaxActivitiesReachedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectManagerNotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;

public class ActivitySpecificTest {

	private Scheduler scheduler = null;
	private Activity activity = null;
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
	public void testSetBudgettedTime(){
		
	}
	
	
	

}
