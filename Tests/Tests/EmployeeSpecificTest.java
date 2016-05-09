package Tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import SoftwareHouse.Activity;
import SoftwareHouse.Employee;
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

/**
 * @author Niklas
 */
public class EmployeeSpecificTest {

	/**
	 * Niklas
	 */
	private Scheduler scheduler = null;
	@Before
	public void setup()
	{
		Tools t = new Tools();
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

	/**
	 * Niklas
	 */
	@Test
	public void testFailingEquals(){
		Employee m = null;
		Employee m2 = null;
		try {
			m = scheduler.getProject("Navision Stat").getEmployees().get(0);
			m2 = scheduler.getProject("Navision Stat").getEmployees().get(1);
		} catch (Exception e) {
			Assert.fail();
		}
		
		int i = 2;
		assertFalse(m.equals(i));
		assertFalse(m.equals(m2));
		String s = "init";
		assertFalse(m.equals(s));
		assertFalse(m.equals(null));
	}

	/**
	 * Niklas
	 */
	@Test
	public void testgetNumberOfprojects(){
		Employee m = null;
		try {
			m = scheduler.getProject("Navision Stat").getEmployees().get(0);
		} catch (Exception e) {
			Assert.fail();
		}
		assertEquals(m.getNumberOfProjects(),1);
	}

	/**
	 * Niklas
	 */
	@Test
	public void testGetAbsenceActivities(){
		Employee m = null;
		try {
			m = scheduler.getProject("Navision Stat").getEmployees().get(0);
		} catch (Exception e) {
			Assert.fail();
		}
		List<Activity> list = m.getAbsenceActivities();
		assertEquals(list.size(),0);
	}

	/**
	 * Niklas
	 */
	@Test
	public void testAddAbsenceActivity(){
		Employee m = null;
		try {
			m = scheduler.getProject("Navision Stat").getEmployees().get(0);
		} catch (Exception e) {
			Assert.fail();
		}
		
		String[] employees = new String[1];
		employees[0] = "JBS";
		try {
			TestTools.addActivity(scheduler, "Navision Stat", "act", employees);
		} catch (Exception e) {
			Assert.fail();
		}
		try {
			m.addAbsenceActivity(scheduler.getActivity("Navision Stat", "act"));
			List<Activity> list = m.getAbsenceActivities();
			assertEquals(list.size(),1);
		} catch (Exception e){
			Assert.fail();
		}
	} 
	
	
}
